import { useState } from 'react';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';

function Login() {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await api.post('/auth/login', {
                email: email,
                password: password,
            });

            if (response.data.accessToken) {
                // Token'ı localStorage'a kaydet
                localStorage.setItem('token', response.data.accessToken);

                // HATA: Maps yerine navigate kullanılmalıydı
                navigate('/dashboard');

                console.log('Token:', response.data.accessToken);
            } else {
                setError('Beklenmeyen cevap alındı. Lütfen tekrar deneyin.');
            }
        } catch (err) {
            console.error('Login Hatası:', err);
            setError('Giriş başarısız! Email veya şifre hatalı.');
        }
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                {/* col-md-15 yanlış → 12'lik grid, burada 8 kullandık */}
                <div className="col-md-8">
                    <div className="card shadow-lg">
                        {/* p-5 ile iç boşluk verdik */}
                        <div className="card-body p-5">
                            <h2 className="text-center mb-4 fw-bold text-primary">
                                Ticket Sistemine Giriş
                            </h2>

                            {error && <div className="alert alert-danger">{error}</div>}

                            <form onSubmit={handleLogin}>
                                <div className="mb-4">
                                    <label className="form-label fw-bold">Email Adresi</label>
                                    <input
                                        type="email"
                                        className="form-control form-control-lg"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        placeholder="ornek@email.com"
                                        required
                                    />
                                </div>

                                <div className="mb-4">
                                    <label className="form-label fw-bold">Şifre</label>
                                    <input
                                        type="password"
                                        className="form-control form-control-lg"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        placeholder="******"
                                        required
                                    />
                                </div>

                                <button
                                    type="submit"
                                    className="btn btn-primary w-100 btn-lg mt-3"
                                >
                                    Giriş Yap
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
