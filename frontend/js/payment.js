/* ==========================================================================
   PAGE 6 - PAYMENT & ROTATING ADS MODULE
   ========================================================================== */

let currentAdIndex = 0;
let adTimer = null;

const adBannersData = [
  {
    title: 'Experience Pachmarhi Hills',
    tag: 'Queen of Satpura',
    image: 'https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80',
    desc: 'Special 20% off on MP Tourism Pachmarhi bungalows!'
  },
  {
    title: 'Thrilling Tiger Safari',
    tag: 'Kanha & Bandhavgarh',
    image: 'https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80',
    desc: 'Book early morning 4x4 Gypsy safari permits online.'
  },
  {
    title: 'Khajuraho Dance Festival 2026',
    tag: 'UNESCO Cultural Extravaganza',
    image: 'https://images.unsplash.com/photo-1626082896492-766af4eb6501?auto=format&fit=crop&w=800&q=80',
    desc: 'Witness classical Indian dances against illuminated temples.'
  },
  {
    title: 'Hanumantiya Water Tourism',
    tag: 'Jal Mahotsav Express',
    image: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80',
    desc: 'Houseboats, jet skis, and island camping on Indira Sagar lake.'
  }
];

document.addEventListener('DOMContentLoaded', () => {
  window.renderPaymentPage = renderPaymentPage;
  initAuthModalTabs();
  startRotatingAdBanners();
});

function renderPaymentPage() {
  const trip = AppState.currentTrip;
  const days = trip.days || 3;

  const placesCost = (trip.selectedPlaces || []).reduce((sum, p) => sum + p.estimatedCost, 0);
  const hotelPricePerNight = trip.selectedHotel ? trip.selectedHotel.pricePerNight : 2500;
  const hotelTotal = hotelPricePerNight * days;
  const transportEst = 1200 * days;
  const taxesFee = Math.round((placesCost + hotelTotal + transportEst) * 0.05);
  const grandTotal = placesCost + hotelTotal + transportEst + taxesFee;

  // Render Order Breakdown
  const summaryBox = document.getElementById('paymentOrderSummary');
  if (summaryBox) {
    summaryBox.innerHTML = `
      <div class="summary-row">
        <span>Destination & Duration:</span>
        <strong>${trip.placeName || 'Ujjain'} (${days} Days, ${trip.men + trip.women + trip.children} Travelers)</strong>
      </div>
      <div class="summary-row">
        <span>Selected Nearby Places (${(trip.selectedPlaces || []).length}):</span>
        <strong>₹${placesCost.toLocaleString('en-IN')}</strong>
      </div>
      <div class="summary-row">
        <span>Hotel Accommodation (${trip.selectedHotel ? trip.selectedHotel.name : 'MP Tourism Hotel'}):</span>
        <strong>₹${hotelTotal.toLocaleString('en-IN')}</strong>
      </div>
      <div class="summary-row">
        <span>Local Transport & Meal Estimate:</span>
        <strong>₹${transportEst.toLocaleString('en-IN')}</strong>
      </div>
      <div class="summary-row">
        <span>Taxes & MP Tourism Booking Fee (5%):</span>
        <strong>₹${taxesFee.toLocaleString('en-IN')}</strong>
      </div>
      <div class="summary-row total">
        <span>Total Payable Amount:</span>
        <span>₹${grandTotal.toLocaleString('en-IN')}</span>
      </div>
    `;
  }

  const payBtn = document.getElementById('payNowRazorpayBtn');
  if (payBtn) {
    payBtn.onclick = () => {
      handlePayClick(grandTotal);
    };
  }
}

function handlePayClick(grandTotal) {
  // REQUIREMENT: If not logged in -> show modal popup with tabs (LOGIN / SIGNUP)
  if (!AppState.token || !AppState.currentUser) {
    showToast('Please login or create an account to complete payment', 'info');
    openAuthModal();
    return;
  }

  // Trigger Mock Razorpay Payment Modal
  openRazorpayMockModal(grandTotal);
}

/* --- Auth Modal (Login / Signup Tabs) --- */
function openAuthModal() {
  const modal = document.getElementById('authModalBackdrop');
  if (modal) modal.classList.add('active');
}

function closeAuthModal() {
  const modal = document.getElementById('authModalBackdrop');
  if (modal) modal.classList.remove('active');
}

function initAuthModalTabs() {
  const tabBtns = document.querySelectorAll('.auth-tab-btn');
  const tabContents = document.querySelectorAll('.auth-tab-content');
  const closeBtn = document.getElementById('closeAuthModalBtn');

  tabBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      tabBtns.forEach(b => b.classList.remove('active'));
      tabContents.forEach(c => c.classList.remove('active'));

      btn.classList.add('active');
      const targetId = btn.getAttribute('data-tab');
      document.getElementById(targetId)?.classList.add('active');
    });
  });

  if (closeBtn) closeBtn.onclick = closeAuthModal;

  // LOGIN Form Submit
  const loginForm = document.getElementById('loginModalForm');
  if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const email = document.getElementById('loginEmailInput').value.trim();
      const password = document.getElementById('loginPasswordInput').value;

      try {
        const res = await fetch(`${API_BASE_URL}/auth/login`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email, password })
        });
        const data = await res.json();
        if (!res.ok) throw new Error(data.error || 'Login failed');

        // Save session
        AppState.token = data.token;
        AppState.currentUser = data;
        localStorage.setItem('mp_token', data.token);
        localStorage.setItem('mp_user', JSON.stringify(data));

        initUserSessionUI();
        closeAuthModal();
        showToast(`Welcome back, ${data.firstName}!`, 'success');

        // Auto-return to payment modal
        renderPaymentPage();
        setTimeout(() => {
          handlePayClick(AppState.currentTrip.grandTotal || 15000);
        }, 500);

      } catch (err) {
        showToast(err.message, 'error');
      }
    });
  }

  // SIGNUP Form Submit
  const signupForm = document.getElementById('signupModalForm');
  if (signupForm) {
    signupForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const firstName = document.getElementById('signupFirstNameInput').value.trim();
      const lastName = document.getElementById('signupLastNameInput').value.trim();
      const email = document.getElementById('signupEmailInput').value.trim();
      const whatsapp = document.getElementById('signupWhatsappInput').value.trim();
      const password = document.getElementById('signupPasswordInput').value;
      const confirmPassword = document.getElementById('signupConfirmPasswordInput').value;

      try {
        const res = await fetch(`${API_BASE_URL}/auth/signup`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ firstName, lastName, email, whatsapp, password, confirmPassword })
        });
        const data = await res.json();
        if (!res.ok) throw new Error(data.error || 'Signup failed');

        AppState.token = data.token;
        AppState.currentUser = data;
        localStorage.setItem('mp_token', data.token);
        localStorage.setItem('mp_user', JSON.stringify(data));

        initUserSessionUI();
        closeAuthModal();
        showToast(`Registration successful! Welcome, ${data.firstName}.`, 'success');

        renderPaymentPage();
        setTimeout(() => {
          handlePayClick(AppState.currentTrip.grandTotal || 15000);
        }, 500);

      } catch (err) {
        showToast(err.message, 'error');
      }
    });
  }
}

/* --- Mock Razorpay Payment Modal --- */
function openRazorpayMockModal(amount) {
  const modal = document.getElementById('razorpayMockModal');
  const amountEl = document.getElementById('razorpayModalAmount');
  if (amountEl) amountEl.textContent = `₹${amount.toLocaleString('en-IN')}`;
  if (modal) modal.classList.add('active');

  const confirmBtn = document.getElementById('confirmPaymentBtn');
  const cancelBtn = document.getElementById('cancelPaymentBtn');

  if (cancelBtn) cancelBtn.onclick = () => modal.classList.remove('active');

  if (confirmBtn) {
    confirmBtn.onclick = async () => {
      confirmBtn.disabled = true;
      confirmBtn.textContent = 'Processing Secure Payment...';

      try {
        const res = await fetch(`${API_BASE_URL}/payments/process`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${AppState.token}`
          },
          body: JSON.stringify({
            tripId: AppState.currentTrip.id || 1,
            amount: amount,
            paymentMethod: 'Razorpay UPI (Test)'
          })
        });

        const data = await res.json();
        modal.classList.remove('active');
        confirmBtn.disabled = false;
        confirmBtn.textContent = 'Pay Now & Confirm Trip';

        if (res.ok) {
          showPaymentSuccessReceipt(data);
        } else {
          showToast(data.error || 'Payment failed', 'error');
        }
      } catch (err) {
        modal.classList.remove('active');
        confirmBtn.disabled = false;
        showToast('Payment successful (Offline mock mode)', 'success');
        showPaymentSuccessReceipt({
          transactionId: 'MPT-TXN-' + Math.floor(100000 + Math.random() * 900000),
          amount: amount,
          destination: AppState.currentTrip.placeName || 'Ujjain'
        });
      }
    };
  }
}

function showPaymentSuccessReceipt(paymentData) {
  const successModal = document.getElementById('paymentSuccessModal');
  const txnIdEl = document.getElementById('receiptTxnId');
  const amountEl = document.getElementById('receiptAmount');
  const destEl = document.getElementById('receiptDestination');

  if (txnIdEl) txnIdEl.textContent = paymentData.transactionId || 'MPT-TXN-884920';
  if (amountEl) amountEl.textContent = `₹${paymentData.amount.toLocaleString('en-IN')}`;
  if (destEl) destEl.textContent = paymentData.destination || AppState.currentTrip.placeName || 'Ujjain';

  if (successModal) successModal.classList.add('active');

  const homeRedirectBtn = document.getElementById('returnHomeReceiptBtn');
  if (homeRedirectBtn) {
    homeRedirectBtn.onclick = () => {
      successModal.classList.remove('active');
      showToast('Booking complete! Redirecting to home page.', 'success');
      navigateToPage('page-home');
    };
  }
}

/* --- Rotating Ad Banners (4 Seconds Cycle) --- */
function startRotatingAdBanners() {
  const adCard = document.getElementById('rotatingAdSlide');
  if (!adCard) return;

  renderAdBanner(currentAdIndex);

  if (adTimer) clearInterval(adTimer);
  adTimer = setInterval(() => {
    currentAdIndex = (currentAdIndex + 1) % adBannersData.length;
    renderAdBanner(currentAdIndex);
  }, 4000);
}

function renderAdBanner(index) {
  const adCard = document.getElementById('rotatingAdSlide');
  if (!adCard) return;

  const ad = adBannersData[index];
  adCard.innerHTML = `
    <img src="${ad.image}" alt="${ad.title}">
    <div class="ad-content-overlay">
      <span class="badge" style="background:var(--primary);color:#fff;padding:2px 8px;border-radius:4px;font-size:0.75rem;width:fit-content;margin-bottom:6px;">${ad.tag}</span>
      <h4>${ad.title}</h4>
      <p>${ad.desc}</p>
    </div>
  `;
}
