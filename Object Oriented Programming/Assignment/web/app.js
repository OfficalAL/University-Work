// Simple SPA navigation
function showSection(section) {
    document.getElementById('books-section').style.display = 'none';
    document.getElementById('patrons-section').style.display = 'none';
    document.getElementById('loans-section').style.display = 'none';
    document.getElementById(section + '-section').style.display = 'block';
}

// Placeholder: Load books, patrons, loans from backend (to be implemented)
// Demo data for now
const demoBooks = [
  { id: 1, title: "1984", author: "George Orwell", year: "1949", publisher: "Secker & Warburg", onLoan: false },
  { id: 2, title: "Brave New World", author: "Aldous Huxley", year: "1932", publisher: "Chatto & Windus", onLoan: true }
];
const demoPatrons = [
  { id: 1, name: "Alice Smith", phone: "1234567890", email: "alice@example.com", books: [2] },
  { id: 2, name: "Bob Jones", phone: "0987654321", email: "bob@example.com", books: [] }
];
const demoLoans = [
  { id: 1, bookId: 2, patronId: 1, dueDate: "2025-12-20" }
];

function loadBooks() {
  let html = '<table><tr><th></th><th>Title</th><th>Author</th><th>Year</th><th>Publisher</th><th>Status</th><th>Options</th></tr>';
  for (const book of demoBooks) {
    html += `<tr>
      <td><img src="../doc/script-dir/images/ui-icons_2e83ff_256x240.png" alt="Book" style="height:20px;"></td>
      <td>${book.title}</td>
      <td>${book.author}</td>
      <td>${book.year}</td>
      <td>${book.publisher}</td>
      <td>${book.onLoan ? '<span style=\'color:red\'>On Loan</span>' : '<span style=\'color:green\'>Available</span>'}</td>
      <td>
        <button onclick="showBookDetails(${book.id})">Details</button>
        <button onclick="deleteBook(${book.id})">Delete</button>
        <button onclick="borrowBook(${book.id})">Borrow</button>
        <button onclick="returnBook(${book.id})">Return</button>
        <button onclick="renewBook(${book.id})">Renew</button>
      </td>
    </tr>`;
  }
  html += '</table>';
  document.getElementById('books-list').innerHTML = html;
}
function loadPatrons() {
  let html = '<table><tr><th></th><th>Name</th><th>Phone</th><th>Email</th><th>Books Borrowed</th><th>Options</th></tr>';
  for (const patron of demoPatrons) {
    html += `<tr>
      <td><img src="../doc/script-dir/images/ui-icons_454545_256x240.png" alt="Patron" style="height:20px;"></td>
      <td>${patron.name}</td>
      <td>${patron.phone}</td>
      <td>${patron.email}</td>
      <td>${patron.books.length}</td>
      <td>
        <button onclick="showPatronDetails(${patron.id})">Details</button>
        <button onclick="deletePatron(${patron.id})">Delete</button>
        <button onclick="showPatronBooks(${patron.id})">Show Books</button>
      </td>
    </tr>`;
  }
  html += '</table>';
  document.getElementById('patrons-list').innerHTML = html;
}
function loadLoans() {
  let html = '<table><tr><th></th><th>Book</th><th>Patron</th><th>Due Date</th><th>Options</th></tr>';
  for (const loan of demoLoans) {
    const book = demoBooks.find(b => b.id === loan.bookId);
    const patron = demoPatrons.find(p => p.id === loan.patronId);
    html += `<tr>
      <td><img src="../doc/script-dir/images/ui-icons_cd0a0a_256x240.png" alt="Loan" style="height:20px;"></td>
      <td>${book ? book.title : ''}</td>
      <td>${patron ? patron.name : ''}</td>
      <td>${loan.dueDate}</td>
      <td>
        <button onclick="returnBook(${loan.id})">Return</button>
        <button onclick="renewLoan(${loan.id})">Renew</button>
      </td>
    </tr>`;
  }
  html += '</table>';
  document.getElementById('loans-list').innerHTML = html;
// Additional placeholder handlers for new buttons
function borrowBook(id) { alert('Borrow book ' + id); }
function renewBook(id) { alert('Renew book ' + id); }
function showPatronBooks(id) { alert('Show books for patron ' + id); }
}

// Placeholder action handlers
function showBookDetails(id) { alert('Show details for book ' + id); }
function deleteBook(id) { alert('Delete book ' + id); }
function showPatronDetails(id) { alert('Show details for patron ' + id); }
function deletePatron(id) { alert('Delete patron ' + id); }
function returnBook(id) { alert('Return book for loan ' + id); }
function renewLoan(id) { alert('Renew loan ' + id); }

// Modal logic (for add/edit forms)
function openAddBookModal() {
    document.getElementById('modal').innerHTML = `
      <div class="modal-content">
        <h3>Add Book</h3>
        <form id="addBookForm">
          <label>Title: <input type="text" name="title" required></label><br><br>
          <label>Author: <input type="text" name="author" required></label><br><br>
          <label>Publication Year: <input type="text" name="year" required></label><br><br>
          <label>Publisher: <input type="text" name="publisher" required></label><br><br>
          <button type="submit">Add</button>
          <button type="button" onclick="closeModal()">Cancel</button>
        </form>
      </div>
    `;
    document.getElementById('modal').style.display = 'flex';
    document.getElementById('addBookForm').onsubmit = function(e) {
        e.preventDefault();
        // TODO: Send to backend
        closeModal();
    };
}
function openAddPatronModal() {
    document.getElementById('modal').innerHTML = `
      <div class="modal-content">
        <h3>Add Patron</h3>
        <form id="addPatronForm">
          <label>Name: <input type="text" name="name" required></label><br><br>
          <label>Phone: <input type="text" name="phone" required></label><br><br>
          <label>Email: <input type="email" name="email" required></label><br><br>
          <button type="submit">Add</button>
          <button type="button" onclick="closeModal()">Cancel</button>
        </form>
      </div>
    `;
    document.getElementById('modal').style.display = 'flex';
    document.getElementById('addPatronForm').onsubmit = function(e) {
        e.preventDefault();
        // TODO: Send to backend
        closeModal();
    };
}
function closeModal() {
    document.getElementById('modal').style.display = 'none';
}

// On page load, load all lists
window.onload = function() {
    loadBooks();
    loadPatrons();
    loadLoans();
};
