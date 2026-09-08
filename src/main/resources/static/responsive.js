/* =========================================================
   SERVICO - Mobile responsive behaviour
   Progressive enhancement only: injects the nav hamburger and
   makes sure every table can scroll inside its own box.
   ========================================================= */
document.addEventListener("DOMContentLoaded", function () {

   // === MOBILE NAV TOGGLE ===
   document.querySelectorAll('.nav-container').forEach(nav => {
      const links = nav.querySelector('.nav-links');
      if (!links || nav.querySelector('.nav-toggle')) return;

      const toggle = document.createElement('button');
      toggle.className = 'nav-toggle';
      toggle.type = 'button';
      toggle.setAttribute('aria-label', 'Toggle navigation menu');
      toggle.setAttribute('aria-expanded', 'false');
      toggle.innerHTML = '<span></span><span></span><span></span>';

      toggle.addEventListener('click', () => {
         const open = links.classList.toggle('open');
         toggle.setAttribute('aria-expanded', open ? 'true' : 'false');
      });

      // Close after tapping a link
      links.querySelectorAll('a').forEach(a => {
         a.addEventListener('click', () => {
            links.classList.remove('open');
            toggle.setAttribute('aria-expanded', 'false');
         });
      });

      const logo = nav.querySelector('.logo');
      if (logo && logo.nextSibling) nav.insertBefore(toggle, logo.nextSibling);
      else nav.appendChild(toggle);
   });

   // Reset the menu when going back to a desktop width
   window.addEventListener('resize', () => {
      if (window.innerWidth > 900) {
         document.querySelectorAll('.nav-links.open').forEach(l => l.classList.remove('open'));
         document.querySelectorAll('.nav-toggle').forEach(t => t.setAttribute('aria-expanded', 'false'));
      }
   });

   // === KEEP TABLES INSIDE THEIR OWN SCROLL BOX ===
   document.querySelectorAll('table').forEach(table => {
      if (table.closest('.table-wrapper')) return;

      const parent = table.parentElement;
      // A plain overflow:auto div already does the job
      if (parent && parent !== document.body &&
         parent.children.length === 1 &&
         getComputedStyle(parent).overflowX === 'auto') {
         parent.classList.add('table-wrapper');
         return;
      }

      const wrapper = document.createElement('div');
      wrapper.className = 'table-wrapper';
      table.parentNode.insertBefore(wrapper, table);
      wrapper.appendChild(table);
   });
});
