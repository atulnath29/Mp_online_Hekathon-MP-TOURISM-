/* ==========================================================================
   PAGE 5 - HOTEL SUGGESTIONS & DAY-WISE ITINERARY MODULE
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
  window.renderHotelsPage = renderHotelsPage;
});

function renderHotelsPage() {
  const currentPlace = AppState.selectedPlaceData || { name: 'Ujjain', hotels: [] };
  const hotels = currentPlace.hotels || getFallbackHotels();

  // Calculate budget headroom for hotel
  const placesCost = AppState.currentTrip.selectedPlaces.reduce((s, p) => s + p.estimatedCost, 0);
  const remainingBudget = AppState.currentTrip.budget - placesCost;
  const days = AppState.currentTrip.days || 1;
  const maxNightlyPrice = Math.max(1200, remainingBudget / days);

  renderHotelsTable(hotels, maxNightlyPrice);

  // Pre-select first hotel fitting budget
  const bestHotel = hotels.find(h => h.pricePerNight * days <= remainingBudget) || hotels[0];
  AppState.currentTrip.selectedHotel = bestHotel;

  renderDaywiseItinerary(bestHotel);

  const proceedPayBtn = document.getElementById('proceedToPaymentBtn');
  if (proceedPayBtn) {
    proceedPayBtn.onclick = () => {
      if (window.renderPaymentPage) {
        window.renderPaymentPage();
      }
      navigateToPage('page-payment');
    };
  }
}

function renderHotelsTable(hotels, maxNightlyPrice) {
  const tbody = document.getElementById('hotelsTableBody');
  if (!tbody) return;
  tbody.innerHTML = '';

  const days = AppState.currentTrip.days || 1;

  hotels.forEach((hotel, idx) => {
    const totalHotelCost = hotel.pricePerNight * days;
    const fitsBudget = totalHotelCost <= (AppState.currentTrip.budget * 0.6);

    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>
        <div class="hotel-cell-info">
          <input type="radio" name="hotelRadio" value="${hotel.id}" ${idx === 0 ? 'checked' : ''}>
          <img src="${hotel.imageUrl}" alt="${hotel.name}">
          <div>
            <strong>${hotel.name}</strong>
            <p style="font-size:0.75rem;color:var(--text-muted);">${(hotel.amenities || []).join(' • ')}</p>
          </div>
        </div>
      </td>
      <td>⭐ ${hotel.rating} / 5</td>
      <td><strong>₹${hotel.pricePerNight.toLocaleString('en-IN')}</strong> / night</td>
      <td>
        ${fitsBudget ? '<span class="badge" style="background:rgba(5,150,105,0.12);color:var(--secondary);padding:4px 8px;border-radius:4px;font-size:0.75rem;font-weight:700;">Fits Budget</span>' : '<span class="badge" style="background:rgba(239,68,68,0.12);color:var(--danger);padding:4px 8px;border-radius:4px;font-size:0.75rem;">Slightly High</span>'}
      </td>
    `;

    tr.querySelector('input[type="radio"]').addEventListener('change', () => {
      AppState.currentTrip.selectedHotel = hotel;
      renderDaywiseItinerary(hotel);
      showToast(`Selected ${hotel.name}`, 'info');
    });

    tbody.appendChild(tr);
  });
}

function renderDaywiseItinerary(selectedHotel) {
  const container = document.getElementById('itineraryDaysContainer');
  if (!container) return;
  container.innerHTML = '';

  const days = AppState.currentTrip.days || 3;
  const placeName = AppState.currentTrip.placeName || 'Ujjain';
  const selectedSpots = AppState.currentTrip.selectedPlaces || [];

  for (let i = 1; i <= days; i++) {
    const card = document.createElement('div');
    card.className = 'day-itinerary-card';

    let morningSpot = i === 1 ? `Arrival at ${placeName} & Check-in at ${selectedHotel ? selectedHotel.name : 'MP Tourism Hotel'}` : `Morning Darshan & Breakfast`;
    let afternoonSpot = selectedSpots[i - 1] ? `${selectedSpots[i - 1].name} Tour (${selectedSpots[i - 1].distanceKm} km away)` : `Local Heritage Sightseeing & Street Food Tour`;
    let eveningSpot = i === days ? `Souvenir Shopping & Departure` : `Scenic River Aarti / Sunset Viewpoint`;

    card.innerHTML = `
      <h3>📅 Day ${i} - ${placeName} Exploration</h3>
      <div class="schedule-slot">🌅 <strong>08:30 AM:</strong> ${morningSpot}</div>
      <div class="schedule-slot">☀️ <strong>01:00 PM:</strong> ${afternoonSpot} - Local Thali Lunch</div>
      <div class="schedule-slot">🌆 <strong>06:00 PM:</strong> ${eveningSpot}</div>
      <div class="schedule-slot">🌙 <strong>09:00 PM:</strong> Dinner & Overnight stay at ${selectedHotel ? selectedHotel.name : 'Selected Hotel'}</div>
    `;

    container.appendChild(card);
  }
}

function getFallbackHotels() {
  return [
    { id: 'h1', name: 'MP Tourism Gateway Resort', rating: 4.7, pricePerNight: 2800, imageUrl: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=600&q=80', amenities: ['Free WiFi', 'Breakfast Included', 'Near Main Temple'] },
    { id: 'h2', name: 'Royal Heritage Hotel', rating: 4.5, pricePerNight: 2200, imageUrl: 'https://images.unsplash.com/photo-1582719508461-905c673771fd?auto=format&fit=crop&w=600&q=80', amenities: ['AC Rooms', 'Restaurant', 'Parking'] },
    { id: 'h3', name: 'Narmada Valley Stay', rating: 4.2, pricePerNight: 1600, imageUrl: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=600&q=80', amenities: ['Budget Friendly', '24h Service'] }
  ];
}
