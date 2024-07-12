
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createWebHistory, createRouter } from "vue-router";
import Home from "@/components/temp/Home.vue";
import Login from "@/components/user/UserLogin.vue";
import Register from "@/components/user/UserRegister.vue";
// lazy-loaded
const Profile = () => import("@/components/user/UserProfile.vue")
const BoardAdmin = () => import("@/components/temp/BoardAdmin.vue")
const BoardModerator = () => import("@/components/temp/BoardModerator.vue")
const BoardUser = () => import("@/components/temp/BoardUser.vue")

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/home",
    component: Home,
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/register",
    component: Register,
  },
  {
    path: "/profile",
    name: "profile",
    // lazy-loaded
    component: Profile,
  },
  {
    path: "/admin",
    name: "admin",
    // lazy-loaded
    component: BoardAdmin,
  },
  {
    path: "/mod",
    name: "moderator",
    // lazy-loaded
    component: BoardModerator,
  },
  {
    path: "/user",
    name: "user",
    // lazy-loaded
    component: BoardUser,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');

//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });

export default router;

// import { createRouter, createWebHistory } from 'vue-router'

// const routes = [
//   {
//     name: "home",
//     path: '/',
//     component: () => import('@/pages/HomePage.vue'),
//   },
//   {
//     name: "auth",
//     path: '/auth',
//     component: () => import('@/pages/AuthPage.vue'),
//   },
//   {
//     name: "profile",
//     path: '/profile',
//     component: () => import('@/pages/UserProfilePage.vue'),
//   },
//   {
//     name: 'cart',
//     path: '/cart',
//     component: () => import('@/pages/CartPage.vue'),
//   }
// ]

// const router = createRouter({
//   history: createWebHistory(import.meta.env.BASE_URL),
//   routes
// })

// export default router
