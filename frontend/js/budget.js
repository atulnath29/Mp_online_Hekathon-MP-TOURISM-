/* ==========================================================================
   PAGE 3 - BUDGET FORM MODULE
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
  initBudgetForm();
});

function initBudgetForm() {
  const form = document.getElementById('budgetForm');
  if (!form) return;

  // Auto-fill user names if logged in
  if (AppState.currentUser) {
    const fnInput = document.getElementById('firstNameInput');
    const lnInput = document.getElementById('lastNameInput');
    if (fnInput && AppState.currentUser.firstName) fnInput.value = AppState.currentUser.firstName;
    if (lnInput && AppState.currentUser.lastName) lnInput.value = AppState.currentUser.lastName;
  }

  // Stepper Buttons Handlers
  setupStepper('menCountInput', 'menMinusBtn', 'menPlusBtn', 0, 10);
  setupStepper('womenCountInput', 'womenMinusBtn', 'womenPlusBtn', 0, 10);
  setupStepper('childrenCountInput', 'childrenMinusBtn', 'childrenPlusBtn', 0, 10);
  setupStepper('tripDaysInput', 'daysMinusBtn', 'daysPlusBtn', 1, 30);

  // Budget live preview updates
  const budgetInput = document.getElementById('totalBudgetInput');
  if (budgetInput) {
    budgetInput.addEventListener('input', updateCostPreview);
  }

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    await handleBudgetSubmit();
  });
}

function setupStepper(inputId, minusId, plusId, minVal, maxVal) {
  const input = document.getElementById(inputId);
  const minus = document.getElementById(minusId);
  const plus = document.getElementById(plusId);
  if (!input || !minus || !plus) return;

  minus.onclick = () => {
    let val = parseInt(input.value) || 0;
    if (val > minVal) {
      input.value = val - 1;
      updateCostPreview();
    }
  };

  plus.onclick = () => {
    let val = parseInt(input.value) || 0;
    if (val < maxVal) {
      input.value = val + 1;
      updateCostPreview();
    }
  };
}

function updateCostPreview() {
  const budget = parseFloat(document.getElementById('totalBudgetInput')?.value || 15000);
  const men = parseInt(document.getElementById('menCountInput')?.value || 0);
  const women = parseInt(document.getElementById('womenCountInput')?.value || 0);
  const children = parseInt(document.getElementById('childrenCountInput')?.value || 0);
  const days = parseInt(document.getElementById('tripDaysInput')?.value || 1);

  const totalTravelers = men + women + children;
  const previewBox = document.getElementById('budgetPreviewPill');

  if (previewBox && totalTravelers > 0 && days > 0) {
    const perPersonDay = Math.round(budget / (totalTravelers * days));
    previewBox.innerHTML = `
      <span>Total Travelers: <strong>${totalTravelers}</strong></span> • 
      <span>Est. Daily Per-Person Budget: <strong>₹${perPersonDay.toLocaleString('en-IN')}</strong></span>
    `;
  }
}

async function handleBudgetSubmit() {
  const firstName = document.getElementById('firstNameInput').value.trim();
  const lastName = document.getElementById('lastNameInput').value.trim();
  const budget = parseFloat(document.getElementById('totalBudgetInput').value);
  const men = parseInt(document.getElementById('menCountInput').value) || 0;
  const women = parseInt(document.getElementById('womenCountInput').value) || 0;
  const children = parseInt(document.getElementById('childrenCountInput').value) || 0;
  const days = parseInt(document.getElementById('tripDaysInput').value) || 1;
  const placeName = document.getElementById('budgetPlaceNameInput')?.value || AppState.selectedPlaceId || 'Ujjain';

  // Client-side validation
  if (!firstName || !lastName) {
    showToast('Please enter your first and last name.', 'error');
    return;
  }
  if (!budget || budget < 1000) {
    showToast('Total budget must be at least ₹1,000.', 'error');
    return;
  }
  if (men + women + children <= 0) {
    showToast('Please include at least 1 traveler.', 'error');
    return;
  }

  const payload = {
    firstName,
    lastName,
    budget,
    men,
    women,
    children,
    days,
    placeName
  };

  try {
    const headers = { 'Content-Type': 'application/json' };
    if (AppState.token) headers['Authorization'] = `Bearer ${AppState.token}`;

    const res = await fetch(`${API_BASE_URL}/trips/calculate`, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify(payload)
    });

    const data = await res.json();
    if (!res.ok) {
      throw new Error(data.error || 'Server validation failed');
    }

    // Save trip context to AppState
    AppState.currentTrip.id = data.tripId;
    AppState.currentTrip.firstName = firstName;
    AppState.currentTrip.lastName = lastName;
    AppState.currentTrip.budget = budget;
    AppState.currentTrip.men = men;
    AppState.currentTrip.women = women;
    AppState.currentTrip.children = children;
    AppState.currentTrip.days = days;
    AppState.currentTrip.placeName = data.placeName;
    AppState.currentTrip.nearbyData = data.nearbyAttractions;
    AppState.currentTrip.selectedPlaces = [];

    showToast('Budget saved! Now select nearby attractions.', 'success');

    if (window.renderNearbyPage) {
      window.renderNearbyPage(data);
    }
    navigateToPage('page-nearby');

  } catch (err) {
    showToast(err.message, 'error');
  }
}
