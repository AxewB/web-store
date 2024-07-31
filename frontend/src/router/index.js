
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createWebHistory, createRouter } from "vue-router";
import Home from "@/pages/HomePage.vue";
// lazy-loaded
const Profile = () => import("@/components/user/UserProfile.vue")
const BoardAdmin = () => import("@/components/user/admin/BoardAdmin.vue")
const BoardModerator = () => import("@/components/temp/BoardModerator.vue")
const BoardUser = () => import("@/components/temp/BoardUser.vue")


const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/cart",
    name: "cart",
    component: () => import("@/components/CartPage.vue"),
  },
  {
    path: "/user/",
    redirect: { name: "userProfile" },
    children: [
      {
        name: "userProfile",
        path: "profile",
        component: () => import("@/components/user/UserProfile.vue"),
      },
      {
        name: "userLogin",
        path: "login",
        component: () => import("@/components/user/UserLogin.vue"),
      },
      {
        name: "userRegister",
        path: "register",
        component: () => import("@/components/user/UserRegister.vue"),
      },

    ]
  },
  {
    path: "/product/",
    children: [
      {
        name: "productPage",
        path: ":id",
        component: () => import("@/components/product/ProductPage.vue")
      }
    ]
  },
  {
    path: "/admin",
    name: "admin",
    component: BoardAdmin,
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