/* ==========================================================================
   MP TOURISM TRIP PLANNER - GLOBAL APP MODULE
   ========================================================================== */

const AppState = {
  currentUser: JSON.parse(localStorage.getItem('mp_user') || 'null'),
  token: localStorage.getItem('mp_token') || null,
  activePage: 'page-home',
  selectedPlaceId: 'ujjain',
  selectedPlaceData: null,
  currentTrip: {
    id: null,
    firstName: '',
    lastName: '',
    budget: 15000,
    men: 1,
    women: 1,
    children: 0,
    days: 3,
    selectedPlaces: [],
    placesCost: 0,
    selectedHotel: null
  },
  theme: localStorage.getItem('mp_theme') || 'light'
};

// API Base URL (Relative for single server run or absolute fallback)
const API_BASE_URL = window.location.origin.includes('8080')
  ? `${window.location.origin}/api`
  : 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
  initTheme();
  initRouter();
  initUserSessionUI();
  initGlobalEvents();
});

/* --- Theme Management --- */
function initTheme() {
  document.documentElement.setAttribute('data-theme', AppState.theme);
  const themeToggle = document.getElementById('themeToggleBtn');
  if (themeToggle) {
    themeToggle.checked = AppState.theme === 'dark';
  }
}

function toggleTheme() {
  AppState.theme = AppState.theme === 'light' ? 'dark' : 'light';
  localStorage.setItem('mp_theme', AppState.theme);
  document.documentElement.setAttribute('data-theme', AppState.theme);
  showToast(`Switched to ${AppState.theme} mode`, 'info');
}

/* --- Router System --- */
function navigateToPage(pageId) {
  const pages = document.querySelectorAll('.page-view');
  pages.forEach(p => p.classList.remove('active'));

  const targetPage = document.getElementById(pageId);
  if (targetPage) {
    targetPage.classList.add('active');
    AppState.activePage = pageId;
    window.scrollTo({ top: 0, behavior: 'smooth' });

    updateStepNavigation(pageId);
    if (window.onPageNavigated) {
      window.onPageNavigated(pageId);
    }
  }
}

function updateStepNavigation(pageId) {
  const stepMap = {
    'page-home': 1,
    'page-details': 2,
    'page-budget': 3,
    'page-nearby': 4,
    'page-hotels': 5,
    'page-payment': 6
  };

  const currentStep = stepMap[pageId] || 1;
  const stepItems = document.querySelectorAll('.step-item');

  stepItems.forEach((item, index) => {
    const stepNum = index + 1;
    item.classList.remove('active', 'completed');
    if (stepNum === currentStep) {
      item.classList.add('active');
    } else if (stepNum < currentStep) {
      item.classList.add('completed');
    }
  });
}

function initRouter() {
  window.addEventListener('hashchange', () => {
    const hash = window.location.hash.replace('#', '');
    if (hash && document.getElementById(hash)) {
      navigateToPage(hash);
    }
  });
}

/* --- User Session & Header UI --- */
function initUserSessionUI() {
  const profileAvatar = document.getElementById('profileAvatarImg');
  const profileInitials = document.getElementById('profileInitialsSpan');
  const userNameHeader = document.getElementById('userNameHeader');
  const userEmailHeader = document.getElementById('userEmailHeader');

  if (AppState.currentUser) {
    if (AppState.currentUser.profileImage) {
      profileAvatar.src = AppState.currentUser.profileImage;
      profileAvatar.style.display = 'block';
      profileInitials.style.display = 'none';
    } else {
      profileAvatar.style.display = 'none';
      profileInitials.style.display = 'block';
      profileInitials.textContent = (AppState.currentUser.firstName[0] || 'U').toUpperCase();
    }
    userNameHeader.textContent = `${AppState.currentUser.firstName} ${AppState.currentUser.lastName}`;
    userEmailHeader.textContent = AppState.currentUser.email;
  } else {
    profileAvatar.style.display = 'none';
    profileInitials.style.display = 'block';
    profileInitials.textContent = '👤';
    userNameHeader.textContent = 'Welcome, Guest';
    userEmailHeader.textContent = 'Click to login/signup';
  }
}

/* --- Global Event Listeners --- */
function initGlobalEvents() {
  // Hamburger drawer
  const hamburgerBtn = document.getElementById('hamburgerBtn');
  const drawerOverlay = document.getElementById('navDrawerOverlay');
  const drawer = document.getElementById('navDrawer');
  const closeDrawerBtn = document.getElementById('closeDrawerBtn');

  if (hamburgerBtn) {
    hamburgerBtn.addEventListener('click', () => {
      drawerOverlay.classList.add('active');
      drawer.classList.add('active');
    });
  }

  const closeDrawer = () => {
    drawerOverlay.classList.remove('active');
    drawer.classList.remove('active');
  };

  if (closeDrawerBtn) closeDrawerBtn.addEventListener('click', closeDrawer);
  if (drawerOverlay) drawerOverlay.addEventListener('click', closeDrawer);

  // Profile dropdown toggle
  const profileBtn = document.getElementById('profileBtn');
  const profileDropdown = document.getElementById('profileDropdown');

  if (profileBtn) {
    profileBtn.addEventListener('click', (e) => {
      e.stopPropagation();
      profileDropdown.classList.toggle('active');
    });
  }

  document.addEventListener('click', (e) => {
    if (profileDropdown && !profileDropdown.contains(e.target) && e.target !== profileBtn) {
      profileDropdown.classList.remove('active');
    }
  });

  // Logout button
  const logoutBtn = document.getElementById('logoutMenuBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      AppState.currentUser = null;
      AppState.token = null;
      localStorage.removeItem('mp_user');
      localStorage.removeItem('mp_token');
      initUserSessionUI();
      showToast('Logged out successfully', 'info');
      profileDropdown.classList.remove('active');
    });
  }
}

/* --- Toast Notifications Helper --- */
function showToast(message, type = 'success') {
  let container = document.getElementById('toastContainer');
  if (!container) {
    container = document.createElement('div');
    container.id = 'toastContainer';
    container.className = 'toast-container';
    document.body.appendChild(container);
  }

  const toast = document.createElement('div');
  toast.className = `toast ${type}`;
  toast.textContent = message;
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateY(-10px)';
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}
