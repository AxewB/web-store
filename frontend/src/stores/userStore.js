import { defineStore } from "pinia";

import AuthService from '@/services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const useUserStore = defineStore("user", {
  state: () => initialState,
  actions: {
    /**
     * Логин с использованием логина и пароля
     * @param {Object} user - Пользователь
     * @returns {Promise}
     */
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
    /**
     * Выход из аккаунта
     */
    logout() {
      AuthService.logout();
      this.logoutSuccess();
    },

    /**
     * Регистрация пользователя
     * @param {Object} user - Новый пользователь
     * @returns {Promise}
     */
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

    /**
     * Назначение пользователя после успешного логина
     * @param {Object} user - Пользователь
     */
    loginSuccess(user) {
      this.status.loggedIn = true;
      this.user = user;
    },

    /**
     * Очистка статуса в случае ошибки при входе
     */
    loginFailure() {
      this.status.loggedIn = false;
      this.user = null;
    },

    /**
     * Очистка статуса после выхода из аккаунта
     */
    logoutSuccess() {
      this.status.loggedIn = false;
      this.user = null;
    },
    /**
     * Очистка статуса после успешной регистрации
     */
    registerSuccess() {
      this.status.loggedIn = false;
    },

    /**
     * Очистка статуса в случае ошибки при регистрации
     */
    registerFailure() {
      this.status.loggedIn = false;
    }
  }
})