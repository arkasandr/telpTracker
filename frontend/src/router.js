import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import Login from './views/Login.vue';
import Register from './views/Register.vue';

Vue.use(Router);

export const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home,
            meta: {
                auth: true,
                title: 'TELPTracker'
            }
        },
        {
            path: '/home',
            component: Home,
            meta: {
                auth: true,
                title: 'TELPTracker'
            }
        },
        {
            path: '/login',
            component: Login,
            meta: {
                auth: true,
                title: 'Login'
            }
        },
        {
            path: '/register',
            component: Register,
            meta: {
                auth: true,
                title: 'Регистрация'
            }
        },
        {
            path: '/profile',
            name: 'profile',
            meta: {
                auth: true,
                title: 'Профиль'
            },
            // lazy-loaded
            component: () => import('./views/Profile.vue')
        },
        {
            path: '/admin',
            name: 'admin',
            // lazy-loaded
            component: () => import('./views/BoardAdmin.vue')
        },
        {
            path: '/mod',
            name: 'moderator',
            // lazy-loaded
            component: () => import('./views/BoardModerator.vue')
        },
        {
            path: '/user',
            name: 'user',
            // lazy-loaded
            component: () => import('./views/BoardUser.vue')
        },
        {
            path: '/tasks',
            name: 'adminTasks',
            meta: {
                auth: true,
                title: 'Задачи'
            },
            // lazy-loaded
            component: () => import('./views/AdminTasks.vue')
        },

        {
            path: '/tasks/:Pid',
            name: 'currentTask',
            meta: {
                auth: true,
                title: ':Pid'
            },
            // lazy-loaded
            component: () => import('./views/CurrentTask.vue')
        }
    ]
});