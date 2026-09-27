/* ==========================================================================
   PAGE 2 - PLACE DETAILS MODULE
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
  window.loadPlaceDetails = loadPlaceDetails;
  initSidePanelEvents();
});

async function loadPlaceDetails(placeId) {
  let placeData = null;
  try {
    const res = await fetch(`${API_BASE_URL}/places/${placeId}`);
    if (res.ok) {
      placeData = await res.json();
    }
  } catch (err) {
    console.warn('Backend fetch failed, using local place data', err);
  }

  if (!placeData) {
    const all = getFallbackDestinations();
    placeData = all.find(d => d.id === placeId) || all[0];
    placeData.gallery = getFallbackGallery(placeId);
  }

  AppState.selectedPlaceData = placeData;

  // Render Page 2 Header Banner
  const heroContainer = document.getElementById('placeHeroContainer');
  if (heroContainer) {
    heroContainer.innerHTML = `
      <img src="${placeData.heroImage}" alt="${placeData.name}">
      <div class="place-hero-overlay">
        <span class="badge" style="background:var(--primary);color:#FFF;padding:4px 12px;border-radius:20px;font-size:0.8rem;width:fit-content;margin-bottom:8px;">${placeData.category}</span>
        <h1>${placeData.name}</h1>
        <div class="place-hero-meta">
          <span>⭐ ${placeData.rating} Rating</span>
          <span>•</span>
          <span>🕒 Best Season: ${placeData.bestTime}</span>
          <span>•</span>
          <span>📍 Madhya Pradesh</span>
        </div>
      </div>
    `;
  }

  // Render Real Photo Gallery Grid
  const galleryGrid = document.getElementById('placeGalleryGrid');
  if (galleryGrid) {
    galleryGrid.innerHTML = '';
    const spots = placeData.gallery || getFallbackGallery(placeId);

    spots.forEach(spot => {
      const card = document.createElement('div');
      card.className = 'spot-card';
      card.innerHTML = `
        <div class="spot-img-box">
          <img src="${spot.imageUrl}" alt="${spot.title}" loading="lazy">
        </div>
        <div class="spot-card-body">
          <h4>${spot.title}</h4>
          <p>🕒 ${spot.timings || 'Open daily'} • 🎟️ ${spot.entryFee || 'Free'}</p>
          <button class="btn-sm" style="margin-top:8px;color:var(--primary);font-weight:700;">View Details & Story &rarr;</button>
        </div>
      `;

      card.addEventListener('click', () => {
        openSpotDetailPanel(spot);
      });

      galleryGrid.appendChild(card);
    });
  }

  // Update Plan Trip CTA
  const planBtn = document.getElementById('startPlanBtn');
  if (planBtn) {
    planBtn.textContent = `🚀 Plan My Trip to ${placeData.name}`;
    planBtn.onclick = () => {
      AppState.currentTrip.placeName = placeData.name;
      const budgetPlaceNameInput = document.getElementById('budgetPlaceNameInput');
      if (budgetPlaceNameInput) budgetPlaceNameInput.value = placeData.name;
      navigateToPage('page-budget');
    };
  }
}

function openSpotDetailPanel(spot) {
  const drawer = document.getElementById('sidePanelDrawer');
  const banner = document.getElementById('panelSpotImage');
  const title = document.getElementById('panelSpotTitle');
  const timings = document.getElementById('panelSpotTimings');
  const fee = document.getElementById('panelSpotFee');
  const story = document.getElementById('panelSpotStory');

  if (!drawer) return;

  banner.src = spot.imageUrl;
  title.textContent = spot.title;
  timings.textContent = spot.timings || '6:00 AM - 8:00 PM';
  fee.textContent = spot.entryFee || 'Free Entry';
  story.textContent = spot.story || 'Historic spot of rich cultural and spiritual importance in Madhya Pradesh.';

  drawer.classList.add('active');

  // Text to speech audio guide simulation
  const audioBtn = document.getElementById('playAudioGuideBtn');
  if (audioBtn) {
    audioBtn.onclick = () => {
      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
        const utterance = new SpeechSynthesisUtterance(`${spot.title}. ${spot.story}`);
        utterance.rate = 0.95;
        window.speechSynthesis.speak(utterance);
        showToast('Playing audio guide...', 'info');
      } else {
        showToast('Audio guide speech synthesis not supported on this browser', 'info');
      }
    };
  }
}

function initSidePanelEvents() {
  const closeBtn = document.getElementById('closeSidePanelBtn');
  const drawer = document.getElementById('sidePanelDrawer');
  if (closeBtn && drawer) {
    closeBtn.addEventListener('click', () => {
      drawer.classList.remove('active');
      if ('speechSynthesis' in window) window.speechSynthesis.cancel();
    });
  }
}

function getFallbackGallery(placeId) {
  const galleries = {
    ujjain: [
      { id: 'u1', title: 'Mahakaleshwar Jyotirlinga', imageUrl: 'https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80', story: 'One of the 12 sacred Jyotirlingas. Famous for its south-facing sanctum (Dakshinamurti) and morning Bhasma Aarti.', timings: '4:00 AM - 11:00 PM', entryFee: 'Free / VIP ₹250' },
      { id: 'u2', title: 'Harsiddhi Mata Mandir', imageUrl: 'https://images.unsplash.com/photo-1609766857041-ed402ea8069a?auto=format&fit=crop&w=800&q=80', story: 'One of the 51 Shakti Peethas featuring two 50-foot lamp columns illuminated during evening Aarti.', timings: '5:00 AM - 10:00 PM', entryFee: 'Free' },
      { id: 'u3', title: 'Ram Ghat Evening Aarti', imageUrl: 'https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=800&q=80', story: 'Ancient bathing ghat on holy Shipra river, site of the Simhastha Kumbh Mela dip and evening brass lamp Aarti.', timings: '6:00 PM - 7:30 PM', entryFee: 'Free' },
      { id: 'u4', title: 'Kal Bhairav Temple', imageUrl: 'https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80', story: 'Unique temple dedicated to guardian deity Kal Bhairav where liquor is traditionally offered as prasad.', timings: '6:00 AM - 10:00 PM', entryFee: 'Free' }
    ],
    gwalior: [
      { id: 'g1', title: 'Gwalior Fort Ramparts', imageUrl: 'https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80', story: 'babur called it the pearl among fortresses of Hind. Features blue ceramic tilework and Man Singh Palace.', timings: '6:00 AM - 6:00 PM', entryFee: '₹75' },
      { id: 'g2', title: 'Jai Vilas Palace Durbar', imageUrl: 'https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80', story: '19th-century palace blending Italian and Tuscan styles with 3.5-tonne chandeliers and silver dining train.', timings: '10:00 AM - 5:00 PM', entryFee: '₹300' }
    ]
  };
  return galleries[placeId] || galleries['ujjain'];
}
