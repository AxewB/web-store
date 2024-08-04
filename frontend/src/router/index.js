
import { createWebHistory, createRouter } from "vue-router";
import Home from "@/pages/HomePage.vue";
import CartPage from "@/components/CartPage.vue";
import UserProfile from "@/components/user/UserProfile.vue";
import UserLogin from "@/components/user/UserLogin.vue";
import UserRegister from "@/components/user/UserRegister.vue";
import ProductPage from "@/components/product/ProductPage.vue";

import BoardAdmin from "@/components/user/admin/BoardAdmin.vue";


const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/cart",
    name: "cart",
    component: CartPage
  },
  {
    path: "/user/",
    redirect: { name: "userProfile" },
    children: [
      {
        name: "userProfile",
        path: "profile",
        component: UserProfile
      },
      {
        name: "userLogin",
        path: "login",
        component: UserLogin
      },
      {
        name: "userRegister",
        path: "register",
        component: UserRegister
      },

    ]
  },
  {
    path: "/product/",
    children: [
      {
        name: "productPage",
        path: ":id",
        component: ProductPage
      }
    ]
  },
  {
    path: "/admin",
    name: "admin",
    component: BoardAdmin,
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