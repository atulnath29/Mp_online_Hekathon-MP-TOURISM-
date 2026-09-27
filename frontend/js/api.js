/**
 * Madhya Pradesh Tourism - Shared API Client
 * Base URL: http://localhost:8080
 * Handles JWT Storage in sessionStorage, Automatic Auth Headers & Error Handling
 */

const API_BASE_URL = 'http://localhost:8080';
const TOKEN_KEY = 'mpt_jwt_token';
const USER_KEY = 'mpt_user_data';

// ==========================================
// TOKEN & USER SESSION MANAGEMENT
// ==========================================
function getToken() {
  return sessionStorage.getItem(TOKEN_KEY) || null;
}

function setToken(token) {
  if (token) {
    sessionStorage.setItem(TOKEN_KEY, token);
  } else {
    sessionStorage.removeItem(TOKEN_KEY);
  }
}

function getUser() {
  const userStr = sessionStorage.getItem(USER_KEY);
  try {
    return userStr ? JSON.parse(userStr) : null;
  } catch (e) {
    return null;
  }
}

function setUser(userData) {
  if (userData) {
    sessionStorage.setItem(USER_KEY, JSON.stringify(userData));
  } else {
    sessionStorage.removeItem(USER_KEY);
  }
}

function logoutUser() {
  sessionStorage.removeItem(TOKEN_KEY);
  sessionStorage.removeItem(USER_KEY);
}

function isAuthenticated() {
  return !!getToken();
}

// ==========================================
// CORE HTTP REQUEST WRAPPER
// ==========================================
async function apiRequest(endpoint, method = 'GET', body = null, requiresAuth = false) {
  const url = `${API_BASE_URL}${endpoint.startsWith('/') ? endpoint : '/' + endpoint}`;
  
  const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  };

  const token = getToken();
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const options = {
    method: method.toUpperCase(),
    headers: headers
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(url, options);

    // Parse JSON response safely
    let data;
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      try {
        data = await response.json();
      } catch (parseErr) {
        data = { message: 'Something went wrong, please try again' };
      }
    } else {
      const text = await response.text();
      try {
        data = JSON.parse(text);
      } catch (e) {
        // If text is not valid JSON, check if it's a short text message, otherwise fallback
        const cleanText = text ? text.trim() : '';
        data = { message: (cleanText && !cleanText.startsWith('<')) ? cleanText : 'Something went wrong, please try again' };
      }
    }

    if (!response.ok) {
      // Create user-friendly error message
      let errorMessage = data.message || data.error || `Request failed with status ${response.status}`;
      if (response.status === 401 && !data.message && !data.error) {
        errorMessage = 'Invalid credentials or session expired. Please login.';
      }
      const error = new Error(errorMessage);
      error.status = response.status;
      error.data = data;
      throw error;
    }

    return data;
  } catch (err) {
    if (err.name === 'SyntaxError' || err.message.includes('JSON')) {
      err.message = 'Something went wrong, please try again';
    } else if (err.name === 'TypeError' && err.message.includes('Failed to fetch')) {
      err.message = 'Unable to connect to MP Tourism Server (http://localhost:8080). Please ensure the backend is running.';
    }
    throw err;
  }
}

// ==========================================
// API ENDPOINTS METHOD MAP
// ==========================================
const API = {
  // Auth APIs
  signup: async (signupData) => {
    const res = await apiRequest('/api/auth/signup', 'POST', signupData);
    if (res && res.token) {
      setToken(res.token);
      setUser(res);
    }
    return res;
  },

  login: async (loginData) => {
    const res = await apiRequest('/api/auth/login', 'POST', loginData);
    if (res && res.token) {
      setToken(res.token);
      setUser(res);
    }
    return res;
  },

  logout: () => {
    logoutUser();
  },

  getCurrentUser: () => getUser(),
  getToken: () => getToken(),
  isLoggedIn: () => isAuthenticated(),

  // Location APIs
  getLocations: async () => {
    return await apiRequest('/api/locations', 'GET');
  },

  getLocationById: async (id) => {
    return await apiRequest(`/api/locations/${id}`, 'GET');
  },

  getLocationsSummary: async () => {
    return await apiRequest('/api/locations/summary', 'GET');
  },

  // Trip APIs
  createTrip: async (tripData) => {
    return await apiRequest('/api/trips', 'POST', tripData);
  },

  getMyTrips: async () => {
    return await apiRequest('/api/trips/my-trips', 'GET', null, true);
  },

  addPlaceToTrip: async (tripId, placeId) => {
    return await apiRequest(`/api/trips/${tripId}/places/${placeId}`, 'POST');
  },

  removePlaceFromTrip: async (tripId, placeId) => {
    return await apiRequest(`/api/trips/${tripId}/places/${placeId}`, 'DELETE');
  },

  getSuggestedPlaces: async (tripId) => {
    return await apiRequest(`/api/trips/${tripId}/suggestions`, 'GET');
  },

  getSuggestedHotels: async (tripId) => {
    return await apiRequest(`/api/trips/${tripId}/hotels`, 'GET');
  },

  processPayment: async (tripId, paymentData = {}) => {
    return await apiRequest(`/api/trips/${tripId}/payment`, 'POST', paymentData);
  },

  // AI Chat API
  sendAiChat: async (message, context = {}) => {
    return await apiRequest('/api/ai-chat', 'POST', {
      message: message,
      place: context.place || 'Madhya Pradesh',
      budget: context.budget || null,
      days: context.days || null,
      travelers: context.travelers || null,
      page: context.page || 'Chat'
    });
  }
};

// ==========================================
// DEDUPLICATED GLOBAL TOAST NOTIFICATION
// ==========================================
function showToast(message, type = 'info') {
  let container = document.getElementById('toastContainer');
  if (!container) {
    container = document.createElement('div');
    container.id = 'toastContainer';
    container.className = 'toast-container';
    document.body.appendChild(container);
  }

  // Prevent duplicate toasts with identical messages from stacking
  const existingToasts = container.querySelectorAll('.toast');
  for (let t of existingToasts) {
    if (t.innerText.includes(message)) {
      return; // Skip adding duplicate toast
    }
  }

  const toast = document.createElement('div');
  toast.className = `toast toast-${type}`;
  toast.innerHTML = `<span>${type === 'success' ? '✅' : '⚠️'}</span> <span>${message}</span>`;
  container.appendChild(toast);
  setTimeout(() => {
    if (toast.parentNode) toast.remove();
  }, 4000);
}

// Make available globally
window.API = API;
window.getToken = getToken;
window.setToken = setToken;
window.getUser = getUser;
window.setUser = setUser;
window.logoutUser = logoutUser;
window.showToast = showToast;

