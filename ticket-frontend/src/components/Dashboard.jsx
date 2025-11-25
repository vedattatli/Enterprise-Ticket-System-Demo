import { useEffect, useState } from 'react';
import api from '../services/api'; // api.js dosyasını import ettik
import { useNavigate } from 'react-router-dom';

function Dashboard() {
    const [tickets, setTickets] = useState([]);
    const navigate = useNavigate();

    // Sayfa Yüklenince Çalışır
    useEffect(() => {
        fetchTickets();
    }, []);

    const fetchTickets = async () => {
        try {
            // Backend'e istek at (Token api.js içinde otomatik ekleniyor!)
            const response = await api.get('/tickets');
            setTickets(response.data);
        } catch (error) {
            console.error("Ticketları çekerken hata:", error);
            // Eğer token süresi dolmuşsa veya geçersizse Login'e at
            if (error.response && error.response.status === 403) {
                localStorage.removeItem('token');
                navigate('/');
            }
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token'); // Token'ı sil
        navigate('/'); // Login'e gönder
    };

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="text-primary">🎫 Ticket Paneli</h2>
                <button onClick={handleLogout} className="btn btn-danger">Çıkış Yap</button>
            </div>

            <div className="card shadow">
                <div className="card-body">
                    <table className="table table-hover table-striped">
                        <thead className="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Başlık</th>
                                <th>Açıklama</th>
                                <th>Öncelik</th>
                                <th>Durum</th>
                                <th>Oluşturan</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tickets.map((ticket) => (
                                <tr key={ticket.id}>
                                    <td>#{ticket.id}</td>
                                    <td className="fw-bold">{ticket.title}</td>
                                    <td>{ticket.description}</td>
                                    <td>
                                        <span className={`badge ${ticket.priority === 'HIGH' ? 'bg-danger' : 'bg-warning'}`}>
                                            {ticket.priority}
                                        </span>
                                    </td>
                                    <td>
                                        <span className={`badge ${ticket.status === 'OPEN' ? 'bg-success' : 'bg-secondary'}`}>
                                            {ticket.status}
                                        </span>
                                    </td>
                                    <td>{ticket.createdBy}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    {tickets.length === 0 && (
                        <div className="text-center mt-3">Henüz hiç ticket yok.</div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default Dashboard;