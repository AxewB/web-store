import { defineStore } from "pinia";

import AuthService from '@/services/auth.service';
import ResponseHandler from "@/scripts/responseHandler";
import { useRequestStore } from "./requestStore";

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
      useRequestStore().showLoading();
      return AuthService.login(user).then(
        user => {
          this.loginSuccess(user);
          const responseHandler = ResponseHandler.success(user, "Вы успешно вошли в систему")
          useRequestStore().hideLoading(responseHandler);
          return responseHandler;
        },
        error => {
          this.loginFailure();
          const responseHandler = ResponseHandler.error(error, "Произошла ошибка при входе")
          useRequestStore().hideLoading(responseHandler);
          return responseHandler;
        }
      );
    },
    /**
     * Выход из аккаунта
     */
    logout() {
      useRequestStore().hideLoading(ResponseHandler.success({response: "success"}, "Вы вышли из системы"));
      AuthService.logout();
      this.logoutSuccess();
    },

    /**
     * Регистрация пользователя
     * @param {Object} user - Новый пользователь
     * @returns {Promise}
     */
    register(user) {
      useRequestStore().showLoading();
      return AuthService.register(user).then(
        response => {
          this.registerSuccess();
          const responseHandler = ResponseHandler.success(response, "Вы успешно зарегистрировались. Осталось авторизоватся. Перенаправляем...")
          useRequestStore().hideLoading(responseHandler);
          return responseHandler;
        },
        error => {
          this.registerFailure();
          const responseHandler = ResponseHandler.error(error, "Произошла ошибка при регистрации")
          useRequestStore().hideLoading(responseHandler);
          return responseHandler;
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