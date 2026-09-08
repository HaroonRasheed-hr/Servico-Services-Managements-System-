document.addEventListener("DOMContentLoaded", function () {
   console.log("JS loaded");

   // === USER LOGIN ===
   const userLoginForm = document.getElementById("user-login-form");
   if (userLoginForm) {
      userLoginForm.addEventListener("submit", async function (e) {
         e.preventDefault();
         const username = document.getElementById("user-username").value;
         const password = document.getElementById("password").value;

         try {
            const response = await fetch("http://localhost:8080/api/users/login", {
               method: "POST",
               headers: { "Content-Type": "application/x-www-form-urlencoded" },
               body: new URLSearchParams({ username, password })
            });

            const result = await response.text();
            if (result === "success") {
               // Fetch full user details
               const userRes = await fetch(`http://localhost:8080/api/users/${username}`);
               if (!userRes.ok) throw new Error("User details fetch failed");
               const user = await userRes.json();
               localStorage.setItem("user-username", user.username);
               localStorage.setItem("user-email", user.email || "");
               localStorage.setItem("user-phone", user.phone || "");
               localStorage.setItem("user-address", user.address || "");

               window.location.href = 'indexlogin.html';
            }

            else {
               alert("Login failed. Please check your credentials.");
            }
         } catch (error) {
            alert("Login error: " + error.message);
         }
      });
   }

   // === USER SIGNUP ===
   const userSignupForm = document.getElementById("user-signup-form");
   if (userSignupForm) {
      userSignupForm.addEventListener("submit", async function (e) {
         e.preventDefault();
         const user = {
            name: document.getElementById('username').value,
            email: document.getElementById('email').value,
            address: document.getElementById('address').value,
            phone: document.getElementById('phone').value,
            username: document.getElementById('user-username').value,
            password: document.getElementById('password').value
         };

         try {
            const response = await fetch("http://localhost:8080/api/users/signup", {
               method: "POST",
               headers: { "Content-Type": "application/json" },
               body: JSON.stringify(user)
            });

            const result = await response.text();
            alert(result);
            if (result === "Signup successful") {
               window.location.href = "userlogin.html";
            }
         } catch (error) {
            alert("Signup error: " + error.message);
         }
      });
   }

   // === COMPANY SIGNUP ===
   const companySignupForm = document.getElementById("company-signup-form");
   if (companySignupForm) {
      companySignupForm.addEventListener("submit", async function (e) {
         e.preventDefault();
         const data = {
            name: document.getElementById("companyname").value,
            services: document.getElementById("services").value,
            email: document.getElementById("email").value,
            address: document.getElementById("address").value,
            phone: document.getElementById("phone").value,
            companyUsername: document.getElementById("company-username").value,
            password: document.getElementById("password").value
         };

         try {
            const response = await fetch("http://localhost:8080/api/company/signup", {
               method: "POST",
               headers: { "Content-Type": "application/json" },
               body: JSON.stringify(data)
            });

            const message = await response.text();
            if (!response.ok) {
               alert("Error: " + message);
               return;
            }

            alert(message);
            window.location.href = "companylogin.html";
         } catch (err) {
            alert("Signup failed. Try again.");
         }
      });
   }

   // === COMPANY LOGIN ===
   const companyLoginForm = document.getElementById("company-login-form");
   if (companyLoginForm) {
      companyLoginForm.addEventListener("submit", async function (e) {
         e.preventDefault();
         const username = document.getElementById("company-username")?.value.trim();
         const password = document.getElementById("password")?.value.trim();

         if (!username || !password) {
            alert("Please enter both username and password.");
            return;
         }

         try {
            const response = await fetch("http://localhost:8080/api/company/login", {
               method: "POST",
               headers: { "Content-Type": "application/json" },
               body: JSON.stringify({ companyUsername: username, password: password })
            });

            const result = await response.text();

            if (response.ok) {
               alert(result);
               localStorage.setItem("company-username", username);
               localStorage.removeItem("user-username");
               window.location.href = "companydashboard.html";
            } else {
               alert(result);
            }
         } catch (error) {
            alert("Something went wrong. Please try again later.");
         }
      });
   }

   // === PROFILE MENU ===
   const username = localStorage.getItem('user-username');
   const companyUsername = localStorage.getItem('company-username');

   const profileBtn = document.querySelector('.profile-btn');
   const profileMenu = document.querySelector('.profile-menu');
   const profileUsernameDisplay = document.getElementById('profile-username');

   if ((username || companyUsername) && profileBtn && profileUsernameDisplay) {
      profileBtn.style.display = 'inline-block';
      profileUsernameDisplay.textContent = username || companyUsername;

      profileBtn.addEventListener('click', () => {
         profileMenu?.classList.toggle('show');
      });

      document.getElementById('dashboardBtn')?.addEventListener('click', () => {
         if (username) window.location.href = 'userdashboard.html';
         else if (companyUsername) window.location.href = 'companydashboard.html';
      });

      document.getElementById('logoutBtn')?.addEventListener('click', () => {
         localStorage.removeItem('user-username');
         localStorage.removeItem('company-username');
         window.location.href = 'index.html';
      });
   }

   // === THEME TOGGLE ===
   document.documentElement.setAttribute('data-theme', localStorage.getItem('theme') || 'light');
   document.querySelector('.theme-toggle')?.addEventListener('click', () => {
      const newTheme = document.documentElement.getAttribute('data-theme') === 'light' ? 'dark' : 'light';
      document.documentElement.setAttribute('data-theme', newTheme);
      localStorage.setItem('theme', newTheme);
   });

   // === SERVICE FORM SUBMIT ===
   const serviceForm = document.getElementById("serviceForm");
   if (serviceForm) {
      serviceForm.addEventListener("submit", async function (e) {
         e.preventDefault();

         const data = {
            serviceName: document.getElementById("serviceName").value,
            description: document.getElementById("description").value,
            avgPrice: parseFloat(document.getElementById("avgPrice").value),
            premiumPrice: parseFloat(document.getElementById("premiumPrice").value),
            vipPrice: parseFloat(document.getElementById("vipPrice").value),
            openTime: document.getElementById("openTime").value,
            closeTime: document.getElementById("closeTime").value,
            companyName: localStorage.getItem("company-username")
         };

         try {
            const response = await fetch("http://localhost:8080/api/services/add", {
               method: "POST",
               headers: { "Content-Type": "application/json" },
               body: JSON.stringify(data)
            });

            if (response.ok) {
               alert("Service added successfully!");
               serviceForm.reset();
               document.getElementById("addServiceForm").style.display = "none";
               loadServices();
            } else {
               const msg = await response.text();
               alert("Error: " + msg);
            }
         } catch (error) {
            alert("Something went wrong while adding service.");
         }
      });
   }
   // === Edit srvice form ===
   async function loadCompanyAddedServices() {
      const username = localStorage.getItem("company-username");
      if (!username) return;

      try {
         const response = await fetch(`http://localhost:8080/api/services/company/${username}`);
         if (!response.ok) throw new Error("Failed to fetch company services");

         const services = await response.json();
         const tableBody = document.getElementById("companyServiceTableBody");

         if (!tableBody) return;

         if (services.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="8">No services added yet.</td></tr>`;
            return;
         }

         tableBody.innerHTML = services.map(service => `
            <tr>
               <td>${service.serviceName}</td>
               <td>${service.description}</td>
               <td>Rs.${service.avgPrice}</td>
               <td>Rs.${service.premiumPrice}</td>
               <td>Rs.${service.vipPrice}</td>
               <td>${service.openTime}</td>
               <td>${service.closeTime}</td>
                  <td class="action-buttons">
                   <button onclick="editService(${service.id})">Edit</button>
                   <button onclick="deleteService(${service.id})">Delete</button>
                 </td>
            </tr>
         `).join("");
      } catch (error) {
         console.error("Error loading company services:", error);
      }
      function editService(id) {
         alert("Edit service functionality to be implemented. ID: " + id);
      }

      async function deleteService(id) {
         if (!confirm("Are you sure you want to delete this service?")) return;

         try {
            const res = await fetch(`http://localhost:8080/api/services/delete/${id}`, {
               method: "DELETE"
            });

            const msg = await res.text();
            if (res.ok) {
               alert("Service deleted successfully.");
               loadCompanyAddedServices(); // Refresh table
            } else {
               alert("Delete failed: " + msg);
            }
         } catch (err) {
            console.error("Delete error:", err);
            alert("Error deleting service.");
         }
      }

   }
   if (window.location.pathname.includes("companydashboard.html")) {
      loadCompanyBookings();
      loadCompanyAddedServices(); // ✅ Add this here
   }



   // === LOAD SERVICES ===
   loadServices();

   async function loadServices() {
      try {
         const response = await fetch("http://localhost:8080/api/services/all");
         const contentType = response.headers.get("content-type");
         const raw = await response.text();

         if (!contentType || !contentType.includes("application/json")) {
            throw new Error("Expected JSON but got: " + raw.substring(0, 100));
         }

         const services = JSON.parse(raw);
         const grid = document.querySelector(".services-grid");

         if (grid) {
            grid.innerHTML = services.map(service => `
               <div class="service-card">
                  <h3>${service.serviceName}</h3>
                  <p>${service.description}</p>
                  <p><strong>Company:</strong> 
                     <a href="companyprofile.html?company=${encodeURIComponent(service.companyName)}">${service.companyName}</a>
                  </p>
                  <div class="price-block">
                     <p>Average: Rs.${service.avgPrice}</p>
                     <p>Premium: Rs.${service.premiumPrice}</p>
                     <p>VIP: Rs.${service.vipPrice}</p>
                  </div>
                  <button class="book-btn" 
                     data-name="${service.serviceName}" 
                     data-company="${service.companyName}">
                     Book Now
                  </button>
               </div>
            `).join("");

            setupBookingButtons();
         }
      } catch (err) {
         console.error("Service loading failed:", err);
      }
   }

   if (window.location.pathname.includes("companydashboard.html")) {
      loadCompanyBookings();
   }

   if (window.location.pathname.includes("userdashboard.html")) {
      loadUserBookings();
   }
});

// === BOOKING BUTTON SETUP ===
window.setupBookingButtons = function () {
   document.querySelectorAll(".book-btn").forEach(btn => {
      btn.addEventListener("click", function () {
         const isLoggedIn = localStorage.getItem("user-username");
         if (!isLoggedIn || !window.location.pathname.includes("indexlogin.html")) {
            showAuthModal();
            return;
         }

         const serviceName = this.dataset.name;
         const companyName = this.dataset.company;

         localStorage.setItem("selectedServiceName", serviceName);
         localStorage.setItem("selectedCompanyName", companyName);
         localStorage.setItem("selectedCompanyUsername", companyName);

         document.getElementById("bookingModal").style.display = "flex";
      });
   });
};
if (typeof flatpickr !== "undefined") {
   flatpickr("#bookingDate", { minDate: "today" });
   flatpickr("#bookingTime", {
      enableTime: true,
      noCalendar: true,
      dateFormat: "H:i",
      time_24hr: true,
   });
}

// === BOOKING MODAL CONFIRM ===
const confirmBookingBtn = document.getElementById("confirmBookingBtn");
if (confirmBookingBtn) {
   confirmBookingBtn.addEventListener("click", async function () {
      const bookingData = {
         name: localStorage.getItem("user-username"),
         email: localStorage.getItem("user-email"),
         phone: localStorage.getItem("user-phone"),
         address: localStorage.getItem("user-address"),
         serviceName: localStorage.getItem("selectedServiceName"),
         companyName: localStorage.getItem("selectedCompanyName"),
         companyUsername: localStorage.getItem("selectedCompanyUsername"), // new!
         bookingDate: document.getElementById("bookingDate").value,
         bookingTime: document.getElementById("bookingTime").value,
         serviceType: document.getElementById("servicetype").value,
         serviceLocation: document.getElementById("serviceLocation").value
      };


      if (!bookingData.bookingDate || !bookingData.bookingTime || !bookingData.serviceType || !bookingData.serviceLocation) {
         alert("Please fill in all booking fields");
         return;
      }

      try {
         const response = await fetch("http://localhost:8080/api/bookings/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bookingData)
         });

         const msg = await response.text();
         if (response.ok) {
            alert(msg);
            document.getElementById("bookingModal").style.display = "none";
         } else {
            alert("Booking failed: " + msg);
         }
      } catch (error) {
         alert("Error confirming booking: " + error.message);
      }
   });
}

// === LOAD COMPANY BOOKINGS ===
async function loadCompanyBookings() {
   const companyName = localStorage.getItem("company-username");
   if (!companyName) return;

   try {
      const res = await fetch(`http://localhost:8080/api/bookings/company/${companyName}`);
      const bookings = await res.json();

      const table = document.getElementById("bookingTableBody");
      if (!table) return;

      if (bookings.length === 0) {
         table.innerHTML = `<tr><td colspan="8">No bookings found.</td></tr>`;
         return;
      }

      table.innerHTML = bookings.map(b => `
         <tr>
            <td>${b.name}</td>
            <td>${b.email}</td>
            <td>${b.phone}</td>
            <td>${b.address}</td>
            <td>${b.serviceName}</td>
            <td>${b.bookingDate}</td>
            <td>${b.bookingTime}</td>
            <td>${b.serviceType}</td>
            <td>${b.serviceLocation}</td>
             <td class="status ${b.status.toLowerCase()}">${b.status}</td>
            <td>
   <div class="action-buttons">
      <button onclick="updateBookingStatus(${b.id}, 'APPROVED')">Approve</button>
      <button onclick="updateBookingStatus(${b.id}, 'REJECTED')">Reject</button>
      <button onclick="updateBookingStatus(${b.id}, 'COMPLETED')">Completed</button>
   </div>
</td>

            <td>${b.review || "No review yet"}</td>            
         </tr>
      `).join("");
   } catch (err) {
      console.error("Failed to load bookings:", err);
   }
}

// === UPDATE BOOKING STATUS ===
async function updateBookingStatus(id, status) {
   try {
      await fetch(`http://localhost:8080/api/bookings/status/${id}?status=${status}`, { method: "PUT" });
      loadCompanyBookings();
   } catch (error) {
      alert("Failed to update status.");
   }
}

// === LOAD USER BOOKINGS ===
async function loadUserBookings() {
   const user = localStorage.getItem("user-username");
   if (!user) return;

   try {
      const res = await fetch(`http://localhost:8080/api/bookings/user/${user}`);
      const bookings = await res.json();

      const table = document.getElementById("userBookingTableBody");
      if (!table) return;

      table.innerHTML = bookings.map(b => `
         <tr>
            <td>${b.serviceName}</td>
            <td>${b.companyName}</td>
            <td>${b.bookingDate}</td>
            <td>${b.bookingTime}</td>
            <td>${b.serviceType}</td>
            <td>${b.serviceLocation}</td>
            <td>${b.status}
               ${b.status === 'COMPLETED' && !b.review ? `
                  <br><button class="review-btn" 
                     data-id="${b.id}" 
                     data-service="${b.serviceName}" 
                     data-company="${b.companyName}">
                     Add Review
                  </button>` : b.review ? `<br><span class="rating-out-of">⭐ ${b.rating || 'No'} - ${b.review}</span>` : ''
         }
            </td>
         </tr>
      `).join("");

      // Setup review button event listeners
      document.querySelectorAll('.review-btn').forEach(button => {
         button.addEventListener('click', () => {
            const bookingId = button.dataset.id;
            document.getElementById('reviewModal').style.display = 'flex';
            document.getElementById('reviewModal').dataset.bookingId = bookingId;
         });
      });

   } catch (err) {
      console.error("Error loading user bookings:", err);
   }
}


// === REVIEW BUTTON SETUP ===
document.querySelectorAll('.review-btn').forEach(button => {
   button.addEventListener('click', () => {
      const bookingId = button.dataset.id;
      document.getElementById('reviewModal').style.display = 'flex';
      document.getElementById('reviewModal').dataset.bookingId = bookingId;
   });
});
let selectedRating = 0;
document.querySelectorAll('#starContainer i').forEach(star => {
   star.addEventListener('click', () => {
      selectedRating = parseInt(star.dataset.star);
      document.querySelectorAll('#starContainer i').forEach(s => {
         s.classList.toggle('active', parseInt(s.dataset.star) <= selectedRating);
      });
   });
});
const reviewText = document.getElementById('reviewText');
const wordCount = document.getElementById('wordCount');

reviewText.addEventListener('input', () => {
   const words = reviewText.value.trim().split(/\s+/).filter(Boolean);
   wordCount.textContent = `${words.length}/100 words`;
   if (words.length > 100) {
      reviewText.value = words.slice(0, 100).join(" ");
   }
});
document.querySelector('.submit-review').addEventListener('click', async () => {
   const reviewTextValue = reviewText.value.trim();
   const bookingId = document.getElementById('reviewModal').dataset.bookingId;

   if (!selectedRating || !reviewTextValue) {
      alert("Please provide a star rating and review text.");
      return;
   }

   try {
      const response = await fetch(`http://localhost:8080/api/bookings/review/${bookingId}`, {
         method: "PUT",
         headers: { "Content-Type": "application/json" },
         body: JSON.stringify({
            rating: selectedRating,
            review: reviewTextValue
         })
      });

      if (response.ok) {
         alert("Review submitted!");
         document.getElementById('reviewModal').style.display = 'none';
         loadUserBookings(); // refresh
      } else {
         const msg = await response.text();
         alert("Error submitting review: " + msg);
      }
   } catch (err) {
      alert("Review submission failed.");
   }
});

// Only load reviews if the section is present
const reviewsGrid = document.getElementById("reviewsGrid");
if (reviewsGrid) {
   loadReviews();
}

async function loadReviews() {
   try {
      const res = await fetch("http://localhost:8080/api/bookings/reviews/all");
      console.log("Fetched review response", res);

      if (!res.ok) throw new Error(`Server returned ${res.status}`);

      const text = await res.text();
      console.log("Raw review response text:", text);

      if (!text) {
         console.warn("No review data returned.");
         return;
      }

      const reviews = JSON.parse(text);
      const grid = document.getElementById("reviewsGrid");

      if (!grid) {
         console.error("reviewsGrid not found in DOM");
         return;
      }

      if (reviews.length === 0) {
         grid.innerHTML = `<p>No reviews yet.</p>`;
         return;
      }

      grid.innerHTML = reviews.map(r => `
         <div class="card">
            <h4>${r.serviceName}</h4>
            <p><strong>Company:</strong> ${r.companyName}</p>
            <p><strong>Rating:</strong> ${"⭐".repeat(r.rating)}</p>
            <p><strong>Review:</strong> ${r.review}</p>
         </div>
      `).join("");

   } catch (err) {
      console.error("Failed to load reviews:", err);
   }
}




// === AUTH MODAL ===
function showAuthModal() {
   const modal = document.getElementById('authModal');
   if (!modal) return;
   modal.style.display = 'block';

   document.querySelector('.close-modal')?.addEventListener('click', () => modal.style.display = 'none');
   document.querySelector('.cancel-link')?.addEventListener('click', () => modal.style.display = 'none');
   window.addEventListener('click', (event) => {
      if (event.target === modal) modal.style.display = 'none';
   });
}

//=== company profile ===
if (window.location.pathname.includes("companyprofile.html")) {
   const urlParams = new URLSearchParams(window.location.search);
   const companyUsername = urlParams.get("company");

   console.log("Loaded company profile for:", companyUsername); // ✅ Debug line

   if (companyUsername) {
      loadCompanyProfile(companyUsername);
      loadCompanyServices(companyUsername);
      loadCompanyReviews(companyUsername);
   }
}



async function loadCompanyProfile(username) {
   try {
      const res = await fetch(`http://localhost:8080/api/company/${username}`);
      if (!res.ok) throw new Error("Company not found");
      const company = await res.json();

      document.getElementById("companyName").textContent = company.name;
      document.getElementById("companyEmail").textContent = `Email: ${company.email}`;
      document.getElementById("companyPhone").textContent = `Phone: ${company.phone}`;
      document.getElementById("companyAddress").textContent = `Address: ${company.address}`;
   } catch (err) {
      alert("Failed to load company profile");
   }
}

async function loadCompanyServices(username) {
   try {
      const res = await fetch(`http://localhost:8080/api/services/company/${username}`);
      const services = await res.json();
      const grid = document.getElementById("servicesGrid");

      grid.innerHTML = services.map(service => `
         <div class="card">
            <h3>${service.serviceName}</h3>
            <p>${service.description}</p>
            <p><strong>Average:</strong> Rs.${service.avgPrice}</p>
            <p><strong>Premium:</strong> Rs.${service.premiumPrice}</p>
            <p><strong>VIP:</strong> Rs.${service.vipPrice}</p>
            <p><strong>Timings:</strong> ${service.openTime} - ${service.closeTime}</p>
         </div>
      `).join("");
   } catch (err) {
      alert("Error loading services");
   }
}

async function loadCompanyReviews(username) {
   try {
      const res = await fetch(`http://localhost:8080/api/bookings/reviews/company/${username}`);
      const reviews = await res.json();
      const grid = document.getElementById("reviewsGrid");

      if (reviews.length === 0) {
         grid.innerHTML = `<p>No reviews yet.</p>`;
         return;
      }

      grid.innerHTML = reviews.map(r => `
         <div class="card">
            <h3>${r.serviceName}</h3>
            <p><strong>Rating:</strong> <span class="rating-stars">${"⭐".repeat(r.rating)}</span></p>
            <p><strong>Review:</strong> ${r.review}</p>
         </div>
      `).join("");
   } catch (err) {
      console.error("Review loading failed:", err);
   }
}
