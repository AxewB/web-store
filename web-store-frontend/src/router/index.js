
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    name: "home",
    path: '/',
    component: () => import('@/pages/HomePage.vue'),
  },
  {
    name: "auth",
    path: '/auth',
    component: () => import('@/pages/AuthPage.vue'),
  },
  {
    name: "register",
    path: "/register",
    component: () => import("@/pages/RegistrationPage.vue")
  },
  {
    name: "profile",
    path: '/profile',
    component: () => import('@/pages/UserProfilePage.vue'),
  },
  {
    name: 'cart',
    path: '/cart',
    component: () => import('@/pages/CartPage.vue'),
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
