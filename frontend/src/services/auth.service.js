import axios from 'axios';

// const API_URL = 'http://localhost:8080/api/auth/';
const API_URL = 'http://195.2.74.216:8881/api/auth/';

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'signin', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                console.log(response.headers);
            // if (response.data.accessToken) {
            localStorage.setItem('user', JSON.stringify(response.data));
        // }

        return response.data;
    });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'signup', {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }
}

export default new AuthService();