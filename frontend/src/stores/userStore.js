import { defineStore } from "pinia";

import AuthService from '@/services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const useUserStore = defineStore("user", {
  state: () => ({
    user: initialState
  }),
  actions: {
    login(user) {
      return AuthService.login(user).then(
        user => {
          this.loginSuccess(user);
          return Promise.resolve(user);
        },
        error => {
          this.loginFailure();
          return Promise.reject(error);
        }
      );
    },
    logout() {
      AuthService.logout();
      this.logoutSuccess();
    },

    register(user) {
      return AuthService.register(user).then(
        response => {
          this.registerSuccess();
          return Promise.resolve(response.data);
        },
        error => {
          this.registerFailure();
          return Promise.reject(error);
        }
      );
    },
    
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logoutSuccess(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    }
  }
})