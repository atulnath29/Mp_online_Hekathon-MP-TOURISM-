/* ==========================================================================
   PROFILE & IMAGE CROPPER MODULE
   ========================================================================== */

let cropImageObj = null;
let cropScale = 1.0;
let cropRotation = 0;

document.addEventListener('DOMContentLoaded', () => {
  initProfileModals();
});

function initProfileModals() {
  // Upload Profile Image Trigger
  const uploadBtn = document.getElementById('uploadAvatarMenuBtn');
  const fileInput = document.getElementById('avatarFileInput');

  if (uploadBtn && fileInput) {
    uploadBtn.onclick = () => {
      if (!AppState.token) {
        showToast('Please login first to update profile photo', 'info');
        openAuthModal();
        return;
      }
      fileInput.click();
    };

    fileInput.onchange = (e) => {
      const file = e.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (evt) => {
          openCropperModal(evt.target.result);
        };
        reader.readAsDataURL(file);
      }
    };
  }

  // Edit Profile Trigger
  const editProfileBtn = document.getElementById('editProfileMenuBtn');
  if (editProfileBtn) {
    editProfileBtn.onclick = () => {
      if (!AppState.token) {
        showToast('Please login to edit profile', 'info');
        openAuthModal();
        return;
      }
      openEditProfileModal();
    };
  }

  // Theme Settings Trigger
  const themeBtn = document.getElementById('themeSettingsMenuBtn');
  if (themeBtn) {
    themeBtn.onclick = () => toggleTheme();
  }
}

/* --- Interactive Canvas Cropper --- */
function openCropperModal(imageSrc) {
  const modal = document.getElementById('cropperModal');
  const canvas = document.getElementById('cropperCanvas');
  const ctx = canvas.getContext('2d');
  if (!modal || !canvas) return;

  modal.classList.add('active');
  cropScale = 1.0;
  cropRotation = 0;

  cropImageObj = new Image();
  cropImageObj.onload = () => {
    drawCropperCanvas();
  };
  cropImageObj.src = imageSrc;

  // Zoom / Rotate Controls
  const zoomIn = document.getElementById('cropZoomInBtn');
  const zoomOut = document.getElementById('cropZoomOutBtn');
  const rotateLeft = document.getElementById('cropRotateLeftBtn');
  const rotateRight = document.getElementById('cropRotateRightBtn');
  const saveBtn = document.getElementById('saveCroppedAvatarBtn');
  const closeBtn = document.getElementById('closeCropperModalBtn');

  if (zoomIn) zoomIn.onclick = () => { cropScale += 0.15; drawCropperCanvas(); };
  if (zoomOut) zoomOut.onclick = () => { if (cropScale > 0.3) cropScale -= 0.15; drawCropperCanvas(); };
  if (rotateLeft) rotateLeft.onclick = () => { cropRotation -= 90; drawCropperCanvas(); };
  if (rotateRight) rotateRight.onclick = () => { cropRotation += 90; drawCropperCanvas(); };
  if (closeBtn) closeBtn.onclick = () => modal.classList.remove('active');

  if (saveBtn) {
    saveBtn.onclick = async () => {
      const croppedBase64 = canvas.toDataURL('image/jpeg', 0.9);
      modal.classList.remove('active');
      await uploadCroppedAvatar(croppedBase64);
    };
  }
}

function drawCropperCanvas() {
  const canvas = document.getElementById('cropperCanvas');
  const ctx = canvas.getContext('2d');
  if (!canvas || !cropImageObj) return;

  const size = 300;
  canvas.width = size;
  canvas.height = size;

  ctx.clearRect(0, 0, size, size);
  ctx.save();

  // Move origin to center for rotation & scaling
  ctx.translate(size / 2, size / 2);
  ctx.rotate((cropRotation * Math.PI) / 180);
  ctx.scale(cropScale, cropScale);

  // Draw image centered
  const aspect = cropImageObj.width / cropImageObj.height;
  let drawW = size;
  let drawH = size;
  if (aspect > 1) {
    drawH = size / aspect;
  } else {
    drawW = size * aspect;
  }

  ctx.drawImage(cropImageObj, -drawW / 2, -drawH / 2, drawW, drawH);
  ctx.restore();

  // Draw circular crop overlay
  ctx.strokeStyle = '#D97706';
  ctx.lineWidth = 4;
  ctx.beginPath();
  ctx.arc(size / 2, size / 2, size / 2 - 4, 0, Math.PI * 2);
  ctx.stroke();
}

async function uploadCroppedAvatar(base64Image) {
  try {
    const res = await fetch(`${API_BASE_URL}/auth/upload-avatar`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${AppState.token}`
      },
      body: JSON.stringify({ imageBase64: base64Image })
    });

    const data = await res.json();
    if (!res.ok) throw new Error(data.error || 'Failed to update avatar');

    AppState.currentUser.profileImage = base64Image;
    localStorage.setItem('mp_user', JSON.stringify(AppState.currentUser));
    initUserSessionUI();
    showToast('Profile photo updated successfully!', 'success');
  } catch (err) {
    showToast(err.message, 'error');
  }
}

/* --- Edit Profile Modal --- */
function openEditProfileModal() {
  const modal = document.getElementById('editProfileModal');
  const fnInput = document.getElementById('editFirstNameInput');
  const lnInput = document.getElementById('editLastNameInput');
  const waInput = document.getElementById('editWhatsappInput');
  const closeBtn = document.getElementById('closeEditProfileModalBtn');
  const form = document.getElementById('editProfileForm');

  if (!modal) return;

  if (AppState.currentUser) {
    if (fnInput) fnInput.value = AppState.currentUser.firstName || '';
    if (lnInput) lnInput.value = AppState.currentUser.lastName || '';
    if (waInput) waInput.value = AppState.currentUser.whatsapp || '';
  }

  modal.classList.add('active');
  if (closeBtn) closeBtn.onclick = () => modal.classList.remove('active');

  if (form) {
    form.onsubmit = async (e) => {
      e.preventDefault();
      try {
        const res = await fetch(`${API_BASE_URL}/auth/update-profile`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${AppState.token}`
          },
          body: JSON.stringify({
            firstName: fnInput.value.trim(),
            lastName: lnInput.value.trim(),
            whatsapp: waInput.value.trim()
          })
        });

        const data = await res.json();
        if (!res.ok) throw new Error(data.error || 'Update failed');

        AppState.currentUser = data;
        localStorage.setItem('mp_user', JSON.stringify(data));
        initUserSessionUI();
        modal.classList.remove('active');
        showToast('Profile updated!', 'success');
      } catch (err) {
        showToast(err.message, 'error');
      }
    };
  }
}
