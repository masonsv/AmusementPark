const API_URL = 'http://localhost:8080/api';

// ==================== INITIALIZATION ====================

document.addEventListener('DOMContentLoaded', () => {
    setupTabs();
    setupActivityTabs();
    setupForms();
    loadInitialData();
});

function setupTabs() {
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');
    
    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            tabs.forEach(t => t.classList.remove('active'));
            tabContents.forEach(tc => tc.classList.remove('active'));
            
            tab.classList.add('active');
            const tabName = tab.getAttribute('data-tab');
            document.getElementById(tabName).classList.add('active');
            
            // Load data when tab is opened
            loadTabData(tabName);
        });
    });
}

function setupActivityTabs() {
    const activityTabs = document.querySelectorAll('.activity-tab');
    const activityForms = document.querySelectorAll('.activity-form');
    
    activityTabs.forEach(tab => {
        tab.addEventListener('click', () => {
            activityTabs.forEach(t => t.classList.remove('active'));
            activityForms.forEach(f => f.classList.remove('active'));
            
            tab.classList.add('active');
            const activity = tab.getAttribute('data-activity');
            document.getElementById(`record-${activity}-form`).classList.add('active');
        });
    });
}

function loadTabData(tabName) {
    console.log('Loading tab:', tabName);
    
    switch(tabName) {
        case 'customers':
            loadCustomers();
            loadTicketsForDropdown();
            break;
        case 'rides':
            loadRides();
            break;
        case 'tickets':
            loadTickets();
            break;
        case 'games':
            loadGames();
            break;
        case 'food':
            loadFoodStalls();
            break;
        case 'analytics':
            loadAllAnalytics();
            break;
    }
}

// ==================== ANALYTICS FUNCTIONS ====================

async function loadAllAnalytics() {
    loadAverageRideRating();
    loadBestRatedRideTypes();
    loadCustomerActivityReport();
    loadAboveAvgWaitTimes();
    loadCustomersWhoDidEverything();
}

async function loadAverageRideRating() {
    const container = document.getElementById('avg-rating-display');
    
    try {
        const response = await fetch(`${API_URL}/analytics/average-ride-rating`);
        const data = await response.json();
        
        container.innerHTML = `
            <div class="metric-value">${data.avgRating.toFixed(2)}</div>
            <div class="metric-label">Average Rating (out of 5)</div>
        `;
        
    } catch (error) {
        console.error('Error loading average rating:', error);
        container.innerHTML = '<p style="color: #e74c3c;">Error loading data</p>';
    }
}

async function loadBestRatedRideTypes() {
    const container = document.getElementById('best-ride-types');
    
    try {
        const response = await fetch(`${API_URL}/analytics/best-rated-ride-types`);
        const rideTypes = await response.json();
        
        if (rideTypes.length === 0) {
            container.innerHTML = '<p style="padding: 20px; color: #999;">No data available</p>';
            return;
        }
        
        container.innerHTML = `
            <table>
                <thead>
                    <tr>
                        <th>Ride Type</th>
                        <th>Average Rating</th>
                    </tr>
                </thead>
                <tbody>
                    ${rideTypes.map(rt => `
                        <tr>
                            <td>${rt.rideType}</td>
                            <td>${rt.avgRating.toFixed(2)} ⭐</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
        
    } catch (error) {
        console.error('Error loading best rated types:', error);
        container.innerHTML = '<p style="color: #e74c3c; padding: 20px;">Error loading data</p>';
    }
}

async function loadCustomerActivityReport() {
    const container = document.getElementById('customer-activity');
    
    try {
        const response = await fetch(`${API_URL}/analytics/customer-activity-report`);
        const customers = await response.json();
        
        if (customers.length === 0) {
            container.innerHTML = '<p style="padding: 20px; color: #999;">No active customers found</p>';
            return;
        }
        
        container.innerHTML = `
            <table>
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Ticket Type</th>
                        <th>Rides</th>
                        <th>Games</th>
                        <th>Food Visits</th>
                    </tr>
                </thead>
                <tbody>
                    ${customers.map(c => `
                        <tr>
                            <td>${c.firstName} ${c.lastName}</td>
                            <td>${c.ticketType}</td>
                            <td>${c.ridesRidden}</td>
                            <td>${c.gamesPlayed}</td>
                            <td>${c.foodEaten}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
        
    } catch (error) {
        console.error('Error loading customer activity:', error);
        container.innerHTML = '<p style="color: #e74c3c; padding: 20px;">Error loading data</p>';
    }
}

async function loadAboveAvgWaitTimes() {
    const container = document.getElementById('above-avg-wait');
    
    try {
        const response = await fetch(`${API_URL}/analytics/above-avg-wait-times`);
        const rides = await response.json();
        
        if (rides.length === 0) {
            container.innerHTML = '<p style="padding: 20px; color: #999;">No rides with above average wait times</p>';
            return;
        }
        
        container.innerHTML = `
            <table>
                <thead>
                    <tr>
                        <th>Ride Name</th>
                        <th>Type</th>
                        <th>Wait Time (min)</th>
                    </tr>
                </thead>
                <tbody>
                    ${rides.map(r => `
                        <tr>
                            <td>${r.rideName}</td>
                            <td>${r.rideType}</td>
                            <td>${r.avgWaitTime.toFixed(1)}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
        
    } catch (error) {
        console.error('Error loading wait times:', error);
        container.innerHTML = '<p style="color: #e74c3c; padding: 20px;">Error loading data</p>';
    }
}

async function loadCustomersWhoDidEverything() {
    const container = document.getElementById('customers-everything');
    
    try {
        const response = await fetch(`${API_URL}/analytics/customers-who-did-everything`);
        const customers = await response.json();
        
        if (customers.length === 0) {
            container.innerHTML = '<p style="padding: 20px; color: #999;">No customers have done everything yet!</p>';
            return;
        }
        
        container.innerHTML = `
            <table>
                <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody>
                    ${customers.map(c => `
                        <tr>
                            <td>${c.customerID}</td>
                            <td>🏆 ${c.firstName} ${c.lastName}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
        
    } catch (error) {
        console.error('Error loading customers who did everything:', error);
        container.innerHTML = '<p style="color: #e74c3c; padding: 20px;">Error loading data</p>';
    }
}

function loadInitialData() {
    loadCustomers();
    loadTicketsForDropdown();
}

// ==================== HELPER FUNCTIONS ====================

function showAlert(message, isSuccess = true) {
    const alertDiv = document.createElement('div');
    alertDiv.className = isSuccess ? 'alert alert-success' : 'alert alert-error';
    alertDiv.textContent = message;
    
    document.body.appendChild(alertDiv);
    
    setTimeout(() => {
        alertDiv.remove();
    }, 3000);
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString();
}

// ==================== CUSTOMERS ====================

async function loadCustomers() {
    const container = document.getElementById('customers-list');
    container.innerHTML = '<div class="loading">Loading customers...</div>';
    
    try {
        const response = await fetch(`${API_URL}/customers`);
        const customers = await response.json();
        
        if (customers.length === 0) {
            container.innerHTML = '<div class="empty-state">No customers found. Add your first customer above!</div>';
            return;
        }
        
        container.innerHTML = customers.map(customer => `
            <div class="card">
                <h4>👤 ${customer.firstName} ${customer.lastName}</h4>
                <p><strong>ID:</strong> ${customer.customerID}</p>
                <p><strong>Height:</strong> ${customer.height}" | <strong>Age:</strong> ${customer.age || 'N/A'}</p>
                <p><strong>Thrill Level:</strong> ${customer.thrillLevel || 'N/A'}/10</p>
                <p><strong>Spent:</strong> $${customer.spent || '0.00'}</p>
                <p><strong>Ticket:</strong> ${customer.ticketType}</p>
                <div class="card-actions">
                    <button class="btn btn-danger btn-small" onclick="deleteCustomer(${customer.customerID})">
                        Delete
                    </button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading customers:', error);
        container.innerHTML = '<div class="alert alert-error">Failed to load customers. Make sure the server is running.</div>';
    }
}

async function loadTicketsForDropdown() {
    try {
        const response = await fetch(`${API_URL}/tickets`);
        const tickets = await response.json();
        
        const selects = document.querySelectorAll('#customer-ticket, #ticket-tickettype');
        selects.forEach(select => {
            select.innerHTML = '<option value="">-- Select Ticket Type --</option>';
            tickets.forEach(ticket => {
                const option = document.createElement('option');
                option.value = ticket.TicketType;
                option.textContent = `${ticket.TicketType} - $${ticket.Price}`;
                select.appendChild(option);
            });
        });
    } catch (error) {
        console.error('Error loading tickets:', error);
    }
}

async function deleteCustomer(customerID) {
    if (!confirm(`Delete customer #${customerID}?`)) return;
    
    try {
        const response = await fetch(`${API_URL}/customers/${customerID}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert('✅ Customer deleted!');
            loadCustomers();
        } else {
            showAlert('❌ ' + result.message, false);
        }
    } catch (error) {
        showAlert('❌ Error deleting customer', false);
    }
}

// Search functionality
document.getElementById('customer-search')?.addEventListener('input', (e) => {
    const searchTerm = e.target.value.toLowerCase();
    const cards = document.querySelectorAll('#customers-list .card');
    
    cards.forEach(card => {
        const text = card.textContent.toLowerCase();
        card.style.display = text.includes(searchTerm) ? 'block' : 'none';
    });
});

// ==================== RIDES ====================

async function loadRides() {
    const container = document.getElementById('rides-list');
    container.innerHTML = '<div class="loading">Loading rides...</div>';
    
    try {
        const response = await fetch(`${API_URL}/rides`);
        const rides = await response.json();
        
        if (rides.length === 0) {
            container.innerHTML = '<div class="empty-state">No rides found. Add your first ride above!</div>';
            return;
        }
        
        container.innerHTML = rides.map(ride => `
            <div class="card">
                <h4>🎢 ${ride.rideName}</h4>
                <p><strong>Type:</strong> ${ride.rideType || 'N/A'}</p>
                <p><strong>Height Req:</strong> ${ride.heightRequirement || 'None'}" | <strong>Capacity:</strong> ${ride.capacity}</p>
                <p><strong>Thrill Level:</strong> ${ride.thrillLevel || 'N/A'}/10 | <strong>Rating:</strong> ${ride.rating || 'N/A'}/5 ⭐</p>
                <p><strong>Ride Time:</strong> ${ride.rideTime || 'N/A'} min | <strong>Wait:</strong> ${ride.avgWaitTime || 'N/A'} min</p>
                <div class="card-actions">
                    <button class="btn btn-danger btn-small" onclick="deleteRide(${ride.rideID})">
                        Delete
                    </button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading rides:', error);
        container.innerHTML = '<div class="alert alert-error">Failed to load rides.</div>';
    }
}

async function deleteRide(rideID) {
    if (!confirm('Delete this ride?')) return;
    
    try {
        const response = await fetch(`${API_URL}/rides/${rideID}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert('✅ Ride deleted!');
            loadRides();
        } else {
            showAlert('❌ ' + result.message, false);
        }
    } catch (error) {
        showAlert('❌ Error deleting ride', false);
    }
}

// Search functionality
document.getElementById('ride-search')?.addEventListener('input', (e) => {
    const searchTerm = e.target.value.toLowerCase();
    const cards = document.querySelectorAll('#rides-list .card');
    
    cards.forEach(card => {
        const text = card.textContent.toLowerCase();
        card.style.display = text.includes(searchTerm) ? 'block' : 'none';
    });
});

// ==================== TICKETS ====================

async function loadTickets() {
    const container = document.getElementById('tickets-list');
    container.innerHTML = '<div class="loading">Loading tickets...</div>';
    
    try {
        const response = await fetch(`${API_URL}/tickets`);
        const tickets = await response.json();
        
        if (tickets.length === 0) {
            container.innerHTML = '<div class="empty-state">No ticket types found. Add your first ticket type above!</div>';
            return;
        }
        
        container.innerHTML = tickets.map(ticket => `
            <div class="card">
                <h4>🎫 ${ticket.TicketType}</h4>
                <p><strong>Price:</strong> $${ticket.Price.toFixed(2)}</p>
                <div class="card-actions">
                    <button class="btn btn-danger btn-small" onclick="deleteTicket('${ticket.TicketType}')">
                        Delete
                    </button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading tickets:', error);
        container.innerHTML = '<div class="alert alert-error">Failed to load tickets.</div>';
    }
}

async function deleteTicket(ticketType) {
    if (!confirm(`Delete ticket type "${ticketType}"?`)) return;
    
    try {
        const response = await fetch(`${API_URL}/tickets/${encodeURIComponent(ticketType)}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert('✅ Ticket type deleted!');
            loadTickets();
            loadTicketsForDropdown();
        } else {
            showAlert('❌ ' + result.message, false);
        }
    } catch (error) {
        showAlert('❌ Error deleting ticket', false);
    }
}

// ==================== GAMES ====================

async function loadGames() {
    const container = document.getElementById('games-list');
    container.innerHTML = '<div class="loading">Loading games...</div>';
    
    try {
        const response = await fetch(`${API_URL}/games`);
        const games = await response.json();
        
        if (games.length === 0) {
            container.innerHTML = '<div class="empty-state">No games found. Add your first game above!</div>';
            return;
        }
        
        container.innerHTML = games.map(game => `
            <div class="card">
                <h4>🎯 ${game.gameName}</h4>
                <p><strong>Game ID:</strong> ${game.gameID} | <strong>Prize ID:</strong> ${game.prizeID || 'N/A'}</p>
                <p><strong>Price:</strong> $${game.price.toFixed(2)}</p>
                <p><strong>Win Odds:</strong> ${game.winOdds ? (game.winOdds * 100).toFixed(0) + '%' : 'N/A'}</p>
                <div class="card-actions">
                    <button class="btn btn-danger btn-small" onclick="deleteGame(${game.gameID})">
                        Delete
                    </button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading games:', error);
        container.innerHTML = '<div class="alert alert-error">Failed to load games.</div>';
    }
}

async function deleteGame(gameID) {
    if (!confirm('Delete this game?')) return;
    
    try {
        const response = await fetch(`${API_URL}/games/${gameID}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert('✅ Game deleted!');
            loadGames();
        } else {
            showAlert('❌ ' + result.message, false);
        }
    } catch (error) {
        showAlert('❌ Error deleting game', false);
    }
}

// ==================== FOOD STALLS ====================

async function loadFoodStalls() {
    const container = document.getElementById('food-list');
    container.innerHTML = '<div class="loading">Loading food stalls...</div>';
    
    try {
        const response = await fetch(`${API_URL}/foodstalls`);
        const stalls = await response.json();
        
        if (stalls.length === 0) {
            container.innerHTML = '<div class="empty-state">No food stalls found. Add your first stall above!</div>';
            return;
        }
        
        container.innerHTML = stalls.map(stall => `
            <div class="card">
                <h4>🍔 ${stall.stallName}</h4>
                <p><strong>Food Type:</strong> ${stall.foodType || 'N/A'}</p>
                <p><strong>Price:</strong> $${stall.price.toFixed(2)}</p>
                <p><strong>Amount Sold:</strong> ${stall.amountSold || 0}</p>
                <div class="card-actions">
                    <button class="btn btn-danger btn-small" onclick="deleteFoodStall(${stall.stallID})">
                        Delete
                    </button>
                </div>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading food stalls:', error);
        container.innerHTML = '<div class="alert alert-error">Failed to load food stalls.</div>';
    }
}

async function deleteFoodStall(stallID) {
    if (!confirm('Delete this food stall?')) return;
    
    try {
        const response = await fetch(`${API_URL}/foodstalls/${stallID}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.status === 'success') {
            showAlert('✅ Food stall deleted!');
            loadFoodStalls();
        } else {
            showAlert('❌ ' + result.message, false);
        }
    } catch (error) {
        showAlert('❌ Error deleting food stall', false);
    }
}
// ==================== ACTIVITIES ====================

async function loadActivityDropdowns() {
    // Load customers for all activity dropdowns
    try {
        const response = await fetch(`${API_URL}/customers`);
        const customers = await response.json();
        
        const customerSelects = document.querySelectorAll('#ride-customer, #game-customer, #food-customer, #ticket-customer');
        customerSelects.forEach(select => {
            select.innerHTML = '<option value="">-- Select Customer --</option>';
            customers.forEach(customer => {
                const option = document.createElement('option');
                option.value = customer.customerID;
                option.textContent = `${customer.firstName} ${customer.lastName} (ID: ${customer.customerID})`;
                select.appendChild(option);
            });
        });
    } catch (error) {
        console.error('Error loading customers for dropdowns:', error);
    }
    
    // Load rides dropdown
    try {
        const response = await fetch(`${API_URL}/rides`);
        const rides = await response.json();
        
        const rideSelect = document.getElementById('ride-ride');
        rideSelect.innerHTML = '<option value="">-- Select Ride --</option>';
        rides.forEach(ride => {
            const option = document.createElement('option');
            option.value = ride.rideID;
            option.textContent = ride.rideName;
            rideSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading rides for dropdown:', error);
    }
    
    // Load games dropdown
    try {
        const response = await fetch(`${API_URL}/games`);
        const games = await response.json();
        
        const gameSelect = document.getElementById('game-game');
        gameSelect.innerHTML = '<option value="">-- Select Game --</option>';
        games.forEach(game => {
            const option = document.createElement('option');
            option.value = game.gameID;
            option.textContent = game.gameName;
            gameSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading games for dropdown:', error);
    }
    
    // Load food stalls dropdown
    try {
        const response = await fetch(`${API_URL}/foodstalls`);
        const stalls = await response.json();
        
        const stallSelect = document.getElementById('food-stall');
        stallSelect.innerHTML = '<option value="">-- Select Stall --</option>';
        stalls.forEach(stall => {
            const option = document.createElement('option');
            option.value = stall.stallID;
            option.textContent = stall.stallName;
            stallSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading stalls for dropdown:', error);
    }
    
    // Tickets already loaded from loadTicketsForDropdown()
}

async function loadActivityHistory() {
    loadRideHistory();
    loadGameHistory();
    loadFoodHistory();
    loadTicketHistory();
}

async function loadRideHistory() {
    const container = document.getElementById('ride-history');
    
    try {
        const response = await fetch(`${API_URL}/ride-on`);
        const history = await response.json();
        
        if (history.length === 0) {
            container.innerHTML = '<p style="color: #999;">No ride history yet</p>';
            return;
        }
        
        container.innerHTML = history.slice(0, 10).map(item => `
            <div class="history-item">
                <p><strong>${item.FirstName} ${item.LastName}</strong> rode <strong>${item.RideName}</strong></p>
                <p class="time">${formatDate(item.RideDate)}</p>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading ride history:', error);
        container.innerHTML = '<p style="color: #e74c3c;">Error loading history</p>';
    }
}

async function loadGameHistory() {
    const container = document.getElementById('game-history');
    
    try {
        const response = await fetch(`${API_URL}/play`);
        const history = await response.json();
        
        if (history.length === 0) {
            container.innerHTML = '<p style="color: #999;">No game history yet</p>';
            return;
        }
        
        container.innerHTML = history.slice(0, 10).map(item => `
            <div class="history-item">
                <p><strong>${item.FirstName} ${item.LastName}</strong> played <strong>${item.GameName}</strong></p>
                <p class="time">${formatDate(item.PlayDate)}</p>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading game history:', error);
        container.innerHTML = '<p style="color: #e74c3c;">Error loading history</p>';
    }
}

async function loadFoodHistory() {
    const container = document.getElementById('food-history');
    
    try {
        const response = await fetch(`${API_URL}/eat-at`);
        const history = await response.json();
        
        if (history.length === 0) {
            container.innerHTML = '<p style="color: #999;">No food purchase history yet</p>';
            return;
        }
        
        container.innerHTML = history.slice(0, 10).map(item => `
            <div class="history-item">
                <p><strong>${item.FirstName} ${item.LastName}</strong> ate at <strong>${item.StallName}</strong></p>
                <p class="time">${formatDate(item.EatDate)}</p>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading food history:', error);
        container.innerHTML = '<p style="color: #e74c3c;">Error loading history</p>';
    }
}

async function loadTicketHistory() {
    const container = document.getElementById('ticket-history');
    
    try {
        const response = await fetch(`${API_URL}/buy-ticket`);
        const history = await response.json();
        
        if (history.length === 0) {
            container.innerHTML = '<p style="color: #999;">No ticket purchase history yet</p>';
            return;
        }
        
        container.innerHTML = history.slice(0, 10).map(item => `
            <div class="history-item">
                <p><strong>${item.FirstName} ${item.LastName}</strong> bought <strong>${item.TicketType}</strong></p>
                <p>Price: $${item.Price.toFixed(2)}</p>
                <p class="time">${formatDate(item.PurchaseDate)}</p>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error loading ticket history:', error);
        container.innerHTML = '<p style="color: #e74c3c;">Error loading history</p>';
    }
}

// ==================== FORM SUBMISSIONS ====================

function setupForms() {
    // Customer Form
    document.getElementById('add-customer-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const customerData = {
            firstName: document.getElementById('customer-firstname').value,
            lastName: document.getElementById('customer-lastname').value,
            height: parseInt(document.getElementById('customer-height').value),
            age: parseInt(document.getElementById('customer-age').value) || null,
            thrillLevel: parseInt(document.getElementById('customer-thrill').value) || null,
            spent: parseFloat(document.getElementById('customer-spent').value) || null,
            ticketType: document.getElementById('customer-ticket').value
        };
        
        try {
            const response = await fetch(`${API_URL}/customers`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(customerData)
            });
            
            const result = await response.json();
            
            if (result.status === 'success') {
                showAlert('✅ Customer added successfully!');
                document.getElementById('add-customer-form').reset();
                loadCustomers();
            } else {
                showAlert('❌ ' + result.message, false);
            }
        } catch (error) {
            showAlert('❌ Error adding customer', false);
        }
    });
    
    // Ride Form
    document.getElementById('add-ride-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const rideData = {
            rideName: document.getElementById('ride-name').value,
            rideType: document.getElementById('ride-type').value || null,
            heightRequirement: parseInt(document.getElementById('ride-height').value) || null,
            capacity: parseInt(document.getElementById('ride-capacity').value) || null,
            thrillLevel: parseInt(document.getElementById('ride-thrill').value) || null,
            rideTime: parseFloat(document.getElementById('ride-time').value) || null,
            avgWaitTime: parseFloat(document.getElementById('ride-wait').value) || null,
            rating: parseFloat(document.getElementById('ride-rating').value) || null
        };
        
        try {
            const response = await fetch(`${API_URL}/rides`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(rideData)
            });
            
            const result = await response.json();
            
            if (result.status === 'success') {
                showAlert('✅ Ride added successfully!');
                document.getElementById('add-ride-form').reset();
                loadRides();
            } else {
                showAlert('❌ ' + result.message, false);
            }
        } catch (error) {
            showAlert('❌ Error adding ride', false);
        }
    });
    
    // Ticket Form
    document.getElementById('add-ticket-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const ticketData = {
            ticketType: document.getElementById('ticket-type').value,
            price: parseFloat(document.getElementById('ticket-price').value)
        };
        
        try {
            const response = await fetch(`${API_URL}/tickets`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(ticketData)
            });
            
            const result = await response.json();
            
            if (result.status === 'success') {
                showAlert('✅ Ticket type added successfully!');
                document.getElementById('add-ticket-form').reset();
                loadTickets();
                loadTicketsForDropdown();
            } else {
                showAlert('❌ ' + result.message, false);
            }
        } catch (error) {
            showAlert('❌ Error adding ticket type', false);
        }
    });
    
    // Game Form
    document.getElementById('add-game-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const gameData = {
            gameName: document.getElementById('game-name').value,
            prizeID: parseInt(document.getElementById('game-prize').value) || null,
            price: parseFloat(document.getElementById('game-price').value),
            winOdds: parseFloat(document.getElementById('game-odds').value) || null
        };
        
        try {
            const response = await fetch(`${API_URL}/games`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(gameData)
            });
            
            const result = await response.json();
            
            if (result.status === 'success') {
                showAlert('✅ Game added successfully!');
                document.getElementById('add-game-form').reset();
                loadGames();
            } else {
                showAlert('❌ ' + result.message, false);
            }
        } catch (error) {
            showAlert('❌ Error adding game', false);
        }
    });
    
    // Food Stall Form
    document.getElementById('add-food-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const foodData = {
            stallName: document.getElementById('food-name').value,
            foodType: document.getElementById('food-type').value || null,
            price: parseFloat(document.getElementById('food-price').value),
            amountSold: parseInt(document.getElementById('food-sold').value) || 0
        };
        
        try {
            const response = await fetch(`${API_URL}/foodstalls`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(foodData)
            });
            
            const result = await response.json();
            
            if (result.status === 'success') {
                showAlert('✅ Food stall added successfully!');
                document.getElementById('add-food-form').reset();
                loadFoodStalls();
            } else {
                showAlert('❌ ' + result.message, false);
            }
        } catch (error) {
            showAlert('❌ Error adding food stall', false);
        }
    });
    
    
}
