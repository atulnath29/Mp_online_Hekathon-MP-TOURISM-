/* ==========================================================================
   PAGE 1 - MP MAP HOME MODULE
   ========================================================================== */

let allDestinationsData = [];

document.addEventListener('DOMContentLoaded', () => {
  fetchDestinations();
  initSearchAutocomplete();
  initCategoryFilters();
});

async function fetchDestinations() {
  try {
    const res = await fetch(`${API_BASE_URL}/places`);
    if (res.ok) {
      allDestinationsData = await res.json();
    } else {
      throw new Error('Failed to fetch places');
    }
  } catch (err) {
    console.warn('Backend offline or fetching error, using client fallback', err);
    allDestinationsData = getFallbackDestinations();
  }
  renderMapPins(allDestinationsData);
  renderDestinationsGrid(allDestinationsData);
}

function renderMapPins(destinations) {
  const pinsContainer = document.getElementById('mapPinsContainer');
  if (!pinsContainer) return;
  pinsContainer.innerHTML = '';

  destinations.forEach(dest => {
    const pin = document.createElement('div');
    pin.className = 'map-pin';
    pin.style.left = `${dest.mapX}%`;
    pin.style.top = `${dest.mapY}%`;
    pin.setAttribute('data-id', dest.id);

    pin.innerHTML = `
      <div class="pin-icon-badge">
        <span>📍</span>
      </div>
      <div class="pin-label">${dest.name}</div>
      <div class="pin-tooltip-card">
        <img src="${dest.heroImage}" alt="${dest.name}">
        <h4>${dest.name}</h4>
        <p>${dest.tagline}</p>
        <span class="badge" style="background:var(--primary);color:#fff;font-size:0.7rem;padding:2px 6px;border-radius:4px;margin-top:4px;display:inline-block;">⭐ ${dest.rating} | ${dest.category}</span>
      </div>
    `;

    pin.addEventListener('click', () => {
      openPlaceDetails(dest.id);
    });

    pinsContainer.appendChild(pin);
  });
}

function renderDestinationsGrid(destinations) {
  const grid = document.getElementById('destinationsGrid');
  if (!grid) return;
  grid.innerHTML = '';

  destinations.forEach(dest => {
    const card = document.createElement('div');
    card.className = 'dest-card';
    card.innerHTML = `
      <div class="dest-card-image">
        <img src="${dest.heroImage}" alt="${dest.name}" loading="lazy">
        <span class="dest-badge">${dest.category}</span>
        <span class="dest-rating">⭐ ${dest.rating}</span>
      </div>
      <div class="dest-card-content">
        <h3>${dest.name}</h3>
        <p>${dest.description}</p>
        <div class="dest-card-footer">
          <span>🕒 ${dest.bestTime}</span>
          <span style="color:var(--primary);font-weight:700;">Explore Gallery &rarr;</span>
        </div>
      </div>
    `;

    card.addEventListener('click', () => {
      openPlaceDetails(dest.id);
    });

    grid.appendChild(card);
  });
}

function openPlaceDetails(placeId) {
  AppState.selectedPlaceId = placeId;
  if (window.loadPlaceDetails) {
    window.loadPlaceDetails(placeId);
  }
  navigateToPage('page-details');
}

/* --- Search Autocomplete --- */
function initSearchAutocomplete() {
  const searchInput = document.getElementById('homeSearchInput');
  const searchDropdown = document.getElementById('searchResultsDropdown');
  if (!searchInput || !searchDropdown) return;

  searchInput.addEventListener('input', (e) => {
    const query = e.target.value.toLowerCase().trim();
    if (!query) {
      searchDropdown.classList.remove('active');
      return;
    }

    const filtered = allDestinationsData.filter(d =>
      d.name.toLowerCase().includes(query) ||
      d.tagline.toLowerCase().includes(query) ||
      d.category.toLowerCase().includes(query)
    );

    if (filtered.length === 0) {
      searchDropdown.innerHTML = `<div style="padding:14px;text-align:center;color:var(--text-muted);">No locations found matching "${query}"</div>`;
    } else {
      searchDropdown.innerHTML = filtered.map(d => `
        <div class="search-item" onclick="openPlaceDetails('${d.id}')">
          <img src="${d.heroImage}" alt="${d.name}">
          <div class="search-item-info">
            <h4>${d.name}</h4>
            <p>${d.tagline} • ⭐ ${d.rating}</p>
          </div>
        </div>
      `).join('');
    }
    searchDropdown.classList.add('active');
  });

  document.addEventListener('click', (e) => {
    if (!searchInput.contains(e.target) && !searchDropdown.contains(e.target)) {
      searchDropdown.classList.remove('active');
    }
  });
}

/* --- Filter Chips --- */
function initCategoryFilters() {
  const filterChips = document.querySelectorAll('.chip-btn');
  filterChips.forEach(chip => {
    chip.addEventListener('click', () => {
      filterChips.forEach(c => c.classList.remove('active'));
      chip.classList.add('active');
      const cat = chip.getAttribute('data-cat');

      if (cat === 'all') {
        renderMapPins(allDestinationsData);
        renderDestinationsGrid(allDestinationsData);
      } else {
        const filtered = allDestinationsData.filter(d => d.category.toLowerCase() === cat.toLowerCase());
        renderMapPins(filtered);
        renderDestinationsGrid(filtered);
      }
    });
  });
}

/* Fallback destinations data matching all 20 MP locations */
function getFallbackDestinations() {
  return [
    { id: 'ujjain', name: 'Ujjain', tagline: 'The City of Mahakal', category: 'Spiritual', mapX: 34, mapY: 59, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80', description: 'Sacred city of Mahakaleshwar Jyotirlinga on Shipra river.', bestTime: 'Oct to Mar' },
    { id: 'gwalior', name: 'Gwalior', tagline: 'Forts & Royalty', category: 'Heritage', mapX: 37, mapY: 15, rating: 4.8, heroImage: 'https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80', description: 'Imposing hill fort, palaces, and musical legacy of Tansen.', bestTime: 'Oct to Mar' },
    { id: 'khajuraho', name: 'Khajuraho', tagline: 'UNESCO Heritage Temples', category: 'Heritage', mapX: 69, mapY: 30, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1626082896492-766af4eb6501?auto=format&fit=crop&w=800&q=80', description: 'World famous Chandela temples with sandstone art.', bestTime: 'Oct to Mar' },
    { id: 'sanchi', name: 'Sanchi', tagline: 'Buddhist Stupa Hub', category: 'Spiritual', mapX: 48, mapY: 48, rating: 4.8, heroImage: 'https://images.unsplash.com/photo-1590050752117-238cb0fb12b1?auto=format&fit=crop&w=800&q=80', description: 'Emperor Ashoka monumental stone stupas.', bestTime: 'Oct to Mar' },
    { id: 'bhopal', name: 'Bhopal', tagline: 'City of Lakes', category: 'Culture', mapX: 44, mapY: 53, rating: 4.7, heroImage: 'https://images.unsplash.com/photo-1582510003544-4d00b7f74220?auto=format&fit=crop&w=800&q=80', description: 'Lakes, tribal museums, and Begum heritage.', bestTime: 'Oct to Mar' },
    { id: 'orchha', name: 'Orchha', tagline: 'Betwa River Gem', category: 'Heritage', mapX: 54, mapY: 28, rating: 4.8, heroImage: 'https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80', description: 'Bundela palaces, river chhatris, and Ram Raja temple.', bestTime: 'Oct to Mar' },
    { id: 'jabalpur', name: 'Jabalpur', tagline: 'Marble Rocks & Falls', category: 'Nature', mapX: 67, mapY: 58, rating: 4.8, heroImage: 'https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80', description: 'Dhuandhar falls and Bhedaghat marble gorge.', bestTime: 'Oct to Apr' },
    { id: 'indore', name: 'Indore', tagline: 'Food Capital', category: 'Culture', mapX: 30, mapY: 62, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?auto=format&fit=crop&w=800&q=80', description: 'Cleanest city, Rajwada, and Sarafa night food.', bestTime: 'Oct to Mar' },
    { id: 'pachmarhi', name: 'Pachmarhi', tagline: 'Queen of Satpura', category: 'Nature', mapX: 52, mapY: 71, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80', description: 'Hill station with waterfalls and Dhoopgarh peak.', bestTime: 'Oct to Jun' },
    { id: 'kanha', name: 'Kanha National Park', tagline: 'Jungle Book Tiger Reserve', category: 'Wildlife', mapX: 78, mapY: 68, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80', description: 'Tiger safari and rare Barasingha swamp deer.', bestTime: 'Oct to Jun' },
    { id: 'omkareshwar', name: 'Omkareshwar', tagline: 'OM Shaped Island', category: 'Spiritual', mapX: 31, mapY: 68, rating: 4.8, heroImage: 'https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80', description: 'Sacred Jyotirlinga island on Narmada river.', bestTime: 'Oct to Mar' },
    { id: 'maheshwar', name: 'Maheshwar', tagline: 'Ahilya Fort & Silk', category: 'Heritage', mapX: 27, mapY: 69, rating: 4.8, heroImage: 'https://images.unsplash.com/photo-1561361513-2d000a50f0dc?auto=format&fit=crop&w=800&q=80', description: 'Ahilyabai Holkar fort, ghats, and Maheshwari sarees.', bestTime: 'Oct to Mar' },
    { id: 'shivpuri', name: 'Shivpuri', tagline: 'Summer Capital', category: 'Heritage', mapX: 39, mapY: 24, rating: 4.6, heroImage: 'https://images.unsplash.com/photo-1600100397608-f010e423b971?auto=format&fit=crop&w=800&q=80', description: 'Scindia chhatris and Madhav national park.', bestTime: 'Oct to Mar' },
    { id: 'bhedaghat', name: 'Bhedaghat', tagline: 'White Marble Gorge', category: 'Nature', mapX: 66, mapY: 59, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80', description: 'Boating between 100ft sparkling marble rocks.', bestTime: 'Nov to May' },
    { id: 'satna', name: 'Satna', tagline: 'Maihar Sharda Shrine', category: 'Spiritual', mapX: 72, mapY: 36, rating: 4.6, heroImage: 'https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80', description: 'Hilltop Maihar Devi ropeway temple.', bestTime: 'Oct to Mar' },
    { id: 'bawangaja', name: 'Bawangaja', tagline: '84-Ft Adinath Statue', category: 'Spiritual', mapX: 21, mapY: 72, rating: 4.7, heroImage: 'https://images.unsplash.com/photo-1544717305-2782549b5136?auto=format&fit=crop&w=800&q=80', description: 'Colossal Jain monolithic statue in Satpura range.', bestTime: 'Oct to Mar' },
    { id: 'mohadi_falls', name: 'Mohadi Falls', tagline: 'Scenic Water Gorge', category: 'Waterfall', mapX: 33, mapY: 64, rating: 4.6, heroImage: 'https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80', description: 'Monsoon waterfall and nature trekking spot.', bestTime: 'Jul to Feb' },
    { id: 'patalpani', name: 'Patalpani Waterfall', tagline: '300-Ft Waterfall', category: 'Waterfall', mapX: 32, mapY: 66, rating: 4.7, heroImage: 'https://images.unsplash.com/photo-1432405972618-c60b0225b8f9?auto=format&fit=crop&w=800&q=80', description: 'High waterfall crossed by heritage rail bridge.', bestTime: 'Jul to Mar' },
    { id: 'mandu', name: 'Mandu', tagline: 'City of Joy & Romance', category: 'Heritage', mapX: 26, mapY: 65, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?auto=format&fit=crop&w=800&q=80', description: 'Floating Jahaz Mahal palace and Rani Roopmati pavilion.', bestTime: 'Oct to Mar' },
    { id: 'bandhavgarh', name: 'Bandhavgarh National Park', tagline: 'Tiger Kingdom', category: 'Wildlife', mapX: 82, mapY: 42, rating: 4.9, heroImage: 'https://images.unsplash.com/photo-1561731216-c3a4d99437d5?auto=format&fit=crop&w=800&q=80', description: 'Highest tiger density and 2000-yr fort.', bestTime: 'Oct to Jun' }
  ];
}
