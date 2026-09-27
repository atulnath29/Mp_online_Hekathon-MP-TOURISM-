/* ==========================================================================
   PAGE 4 - AI SUGGESTED NEARBY PLACES MODULE
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
  window.renderNearbyPage = renderNearbyPage;
});

function renderNearbyPage(tripData) {
  const placeTitle = document.getElementById('nearbyPlaceTitle');
  if (placeTitle) {
    placeTitle.textContent = `Attractions & Experiences near ${tripData.placeName || 'Selected Location'}`;
  }

  const listGrid = document.getElementById('nearbyListGrid');
  if (!listGrid) return;
  listGrid.innerHTML = '';

  const attractions = tripData.nearbyAttractions || AppState.currentTrip.nearbyData || [];
  AppState.currentTrip.selectedPlaces = []; // reset selected

  attractions.forEach(item => {
    const card = document.createElement('div');
    card.className = 'nearby-card';
    card.setAttribute('data-id', item.id);

    card.innerHTML = `
      <img src="${item.imageUrl}" alt="${item.name}">
      <div class="nearby-details">
        <h4>${item.name}</h4>
        <p>📍 ${item.distanceKm} km away • ${item.category || 'Sightseeing'}</p>
        <p style="font-size:0.78rem;color:var(--text-secondary);">${item.description}</p>
        <div class="nearby-action-row" style="margin-top:8px;">
          <span style="font-weight:700;color:var(--primary);">₹${item.estimatedCost.toLocaleString('en-IN')} total</span>
          <button class="toggle-item-btn" id="toggleBtn_${item.id}">+</button>
        </div>
      </div>
    `;

    const btn = card.querySelector(`#toggleBtn_${item.id}`);
    btn.onclick = () => togglePlaceSelection(item, card, btn);

    listGrid.appendChild(card);
  });

  updateBudgetTrackerBar();

  const proceedBtn = document.getElementById('proceedToHotelsBtn');
  if (proceedBtn) {
    proceedBtn.onclick = async () => {
      await saveSelectedPlacesToBackend();
      if (window.renderHotelsPage) {
        window.renderHotelsPage();
      }
      navigateToPage('page-hotels');
    };
  }
}

function togglePlaceSelection(item, card, btn) {
  const index = AppState.currentTrip.selectedPlaces.findIndex(p => p.id === item.id);

  if (index >= 0) {
    // Remove
    AppState.currentTrip.selectedPlaces.splice(index, 1);
    card.classList.remove('added');
    btn.classList.remove('added');
    btn.textContent = '+';
    showToast(`Removed ${item.name} from itinerary`, 'info');
  } else {
    // Add if budget allows
    const currentCost = calculateSelectedPlacesCost();
    const newTotal = currentCost + item.estimatedCost;

    if (newTotal > AppState.currentTrip.budget) {
      showToast(`Adding ${item.name} exceeds your total budget of ₹${AppState.currentTrip.budget}!`, 'error');
      return;
    }

    AppState.currentTrip.selectedPlaces.push(item);
    card.classList.add('added');
    btn.classList.add('added');
    btn.textContent = '✓';
    showToast(`Added ${item.name} to itinerary!`, 'success');
  }

  updateBudgetTrackerBar();
}

function calculateSelectedPlacesCost() {
  return AppState.currentTrip.selectedPlaces.reduce((sum, item) => sum + item.estimatedCost, 0);
}

function updateBudgetTrackerBar() {
  const total = AppState.currentTrip.budget || 15000;
  const allocated = calculateSelectedPlacesCost();
  const remaining = Math.max(0, total - allocated);
  const percent = Math.min(100, Math.round((allocated / total) * 100));

  const totalEl = document.getElementById('trackerTotalVal');
  const allocEl = document.getElementById('trackerAllocatedVal');
  const remEl = document.getElementById('trackerRemainingVal');
  const progressFill = document.getElementById('trackerProgressFill');

  if (totalEl) totalEl.textContent = `₹${total.toLocaleString('en-IN')}`;
  if (allocEl) allocEl.textContent = `₹${allocated.toLocaleString('en-IN')}`;
  if (remEl) {
    remEl.textContent = `₹${remaining.toLocaleString('en-IN')}`;
    remEl.style.color = remaining < total * 0.1 ? 'var(--danger)' : 'var(--secondary)';
  }

  if (progressFill) progressFill.style.width = `${percent}%`;
}

async function saveSelectedPlacesToBackend() {
  if (!AppState.currentTrip.id) return;
  const placesPayload = AppState.currentTrip.selectedPlaces.map(p => ({
    name: p.name,
    cost: p.estimatedCost,
    status: 'ADDED'
  }));

  try {
    await fetch(`${API_BASE_URL}/trips/${AppState.currentTrip.id}/places`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(placesPayload)
    });
  } catch (err) {
    console.warn('Failed syncing places with backend', err);
  }
}
