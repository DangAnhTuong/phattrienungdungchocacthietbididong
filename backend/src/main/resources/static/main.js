// --- CONFIGURATION ---
const GOOGLE_CLIENT_ID = "151027052858-mmdiucsj3s8ddkmfk0rieanmhojts1np.apps.googleusercontent.com";
const FACEBOOK_APP_ID = "1965620300987841";

// --- VALIDATION LOGIC ---
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function handleInputValidation(inputId, iconSuccessId, iconErrorId, errorTextId, validationFn) {
    const input = document.getElementById(inputId);
    if (!input) return;

    input.addEventListener('input', function() {
        const val = this.value.trim();
        const iconSuccess = document.getElementById(iconSuccessId);
        const iconError = document.getElementById(iconErrorId);
        const errorText = document.getElementById(errorTextId);

        if (val === '') {
            this.classList.remove('is-valid', 'is-invalid');
            if (iconSuccess) iconSuccess.classList.add('hidden');
            if (iconError) iconError.classList.add('hidden');
            if (errorText) errorText.classList.add('hidden');
            return;
        }

        const isValid = validationFn(val);
        if (isValid) {
            this.classList.add('is-valid');
            this.classList.remove('is-invalid');
            if (iconSuccess) iconSuccess.classList.remove('hidden');
            if (iconError) iconError.classList.add('hidden');
            if (errorText) errorText.classList.add('hidden');
        } else {
            this.classList.add('is-invalid');
            this.classList.remove('is-valid');
            if (iconSuccess) iconSuccess.classList.add('hidden');
            if (iconError) iconError.classList.remove('hidden');
            if (errorText) errorText.classList.remove('hidden');
        }
    });
}

document.addEventListener('DOMContentLoaded', () => {
    handleInputValidation('emailInput', 'emailSuccess', 'emailError', 'emailErrorText', validateEmail);
});

// --- SOCIAL LOGIN (GOOGLE & FACEBOOK SDK) ---
window.onload = function () {
    // 1. Khởi tạo Google Identity Services
    if (window.google) {
        google.accounts.id.initialize({
            client_id: GOOGLE_CLIENT_ID,
            callback: handleGoogleCredentialResponse,
            context: "signin",
            ux_mode: "popup"
        });
    }
};

// 2. Khởi tạo Facebook JS SDK
window.fbAsyncInit = function() {
    if (window.FB) {
        FB.init({
            appId      : FACEBOOK_APP_ID,
            cookie     : true,                     
            xfbml      : true,                     
            version    : 'v19.0'           
        });
    }
};

function handleGoogleLogin() {
    if (window.google) {
        // Mở popup chọn tài khoản Google (One Tap / Popup)
        google.accounts.id.prompt();
    } else {
        alert("Google SDK chưa được tải xong, vui lòng thử lại.");
    }
}

async function handleGoogleCredentialResponse(response) {
    // response.credential chứa ID Token của Google
    const token = response.credential;
    console.log("Google ID Token received");
    await sendSocialTokenToBackend('/api/auth/google', token);
}

function handleFacebookLogin() {
    if (window.FB) {
        FB.login(function(response) {
            if (response.authResponse) {
                // Lấy Access Token của Facebook
                const token = response.authResponse.accessToken;
                console.log("Facebook Access Token received");
                sendSocialTokenToBackend('/api/auth/facebook', token);
            } else {
                console.log('Người dùng đã hủy đăng nhập Facebook.');
            }
        }, {scope: 'public_profile'}); // Xóa quyền 'email' để tránh lỗi Invalid Scopes
    } else {
        alert("Facebook SDK chưa được tải xong, vui lòng thử lại.");
    }
}

async function sendSocialTokenToBackend(url, token) {
    try {
        const res = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ token: token })
        });
        
        // Có thể server trả về text hoặc json, đọc cẩn thận
        let data = {};
        const textResponse = await res.text();
        try {
            data = JSON.parse(textResponse);
        } catch (e) {
            data = { message: textResponse };
        }

        if (res.ok) {
            alert('Đăng nhập Social thành công!');
            if (data.accessToken) {
                localStorage.setItem('token', data.accessToken);
            }
            // window.location.href = '/dashboard.html';
        } else {
            alert('Lỗi đăng nhập: ' + (data.message || data.error || 'Thất bại'));
        }
    } catch (err) {
        alert('Lỗi kết nối tới server');
        console.error(err);
    }
}

// --- FORM SUBMIT LOGIC ---
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('emailInput').value;
        const password = document.getElementById('passwordInput').value;
        const btn = loginForm.querySelector('button');
        
        btn.disabled = true;
        btn.innerHTML = 'LOADING...';

        try {
            const res = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });
            const data = await res.json();
            if (res.ok) {
                alert('Đăng nhập thành công!');
                localStorage.setItem('token', data.accessToken);
            } else {
                alert('Lỗi: ' + (data.error || data.message || 'Đăng nhập thất bại'));
            }
        } catch (err) {
            alert('Lỗi kết nối');
        } finally {
            btn.disabled = false;
            btn.innerHTML = 'LOGIN';
        }
    });
}

const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const name = document.getElementById('nameInput').value;
        const email = document.getElementById('emailInput').value;
        const password = document.getElementById('passwordInput').value;
        const btn = registerForm.querySelector('button');
        
        btn.disabled = true;
        btn.innerHTML = 'LOADING...';

        try {
            const res = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, password })
            });
            const data = await res.json();
            if (res.ok) {
                alert('Đăng ký thành công!');
                window.location.href = 'login.html';
            } else {
                alert('Lỗi: ' + (data.error || data.message || 'Đăng ký thất bại'));
            }
        } catch (err) {
            alert('Lỗi kết nối');
        } finally {
            btn.disabled = false;
            btn.innerHTML = 'SIGN UP';
        }
    });
}
