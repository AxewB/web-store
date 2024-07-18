
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createWebHistory, createRouter } from "vue-router";
import Home from "@/pages/HomePage.vue";
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
    path: "/user/",
    children: [
      {
        path: "login",
        component: () => import("@/components/user/UserLogin.vue"),
      },
      {
        path: "register",
        component: () => import("@/components/user/UserRegister.vue"),
      },
      {
        path: "profile",
        component: () => import("@/components/user/UserProfile.vue"),
      },
      {
        path: "payment",
        component: () => import("@/components/user/UserPayment.vue"),
      },
      {
        path: "orders",
        component: () => import("@/components/user/UserOrders.vue"),
      }
    ]
  },
  {
    path: "/product/",
    children: [
      {
        path: ":id",
        component: () => import("@/components/product/ProductPage.vue")
      }
    ]
  }
 
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