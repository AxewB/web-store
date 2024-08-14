import {defineStore} from 'pinia';
import { useUserStore } from '@/stores/userStore';
import contentHeader from '@/services/content-header';
import userHeader from '@/services/auth-header';
import axios from 'axios';
import ResponseHandler from '@/scripts/responseHandler';

const API_URL = "http://localhost:8080/api/cart";

export const useCartStore = defineStore('cart', {
  state: () => ({
    cart: [], // Корзина
    totalCost: 0, // Общая стоимость продуктов в наличии
  }),

  getters: {
    /**
     * Проверка наличия продукта в корзине
     * @param {Object} state - Состояние хранилища.
     * @returns {Boolean} - true, если продукт находится в корзине.
     */
    isProductInCart: (state) => {
      return (product) => {
        return state.cart.products.some((item) => item.id === product.id);
      }
    }
  },

  actions: {
    /**
     * Запрос на получение корзины пользователя с сервера
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async getCart() {
      const userStore = useUserStore();
      return axios.get(API_URL + `/${userStore.user.id}`, { headers: userHeader() })
      .then((response) => {
        this.cart = response.data.cart
        this.totalCost = response.data.totalCost
        return ResponseHandler.success(response, "Корзина успешно получена")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении корзины.")
      })
    },
    /**
     * Запрос на добавление продукта в корзину
     * @param {Object} product - Продукт, который будет добавлен.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async addToCart(product) {
      const userStore = useUserStore();
      const { id } = product;
      return axios.post(API_URL + `/${userStore.user.id}/add?productId=${id}`, { headers: contentHeader() })
      .then((response) => {
        this.getCart()
        return ResponseHandler.success(response, "Корзина успешно очищена.")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Ошибка при добавлении продукта в корзину.")
      })
    },
    /**
     * Запрос на удаление продукта из корзины.
     * @param {Object} product - Продукт, который будет удален.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async removeFromCart(product) {
      const userStore = useUserStore();
      const { id } = product;
      return axios.delete(API_URL + `/${userStore.user.id}/remove?productId=${id}`, { headers: userHeader() })
      .then((response) => {
        this.getCart()
        return ResponseHandler.success(response, "Продукт успешно удален из корзины.")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при удалении продукта из корзины.")
      })
    },
    /**
     * Запрос на удаление продукта из корзины (по id)
     * @param {number} productId - id продукта, который будет удален
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async removeFromCartById(productId) {
      const userStore = useUserStore();
      const id = productId;
      return axios.delete(API_URL + `/${userStore.user.id}/remove?productId=${id}`, { headers: userHeader() })
      .then((response) => {
        this.getCart()
        return ResponseHandler.success(response, "Продукт успешно удален из корзины.")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при удалении продукта из корзины.")
      })
    },
    /**
     * Запрос на очистку корзины
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async clearCart() {
      const userStore = useUserStore();
      return axios.delete(API_URL + `/${userStore.user.id}/clear`, { headers: userHeader() })
      .then((response) => {
        this.getCart()
        return ResponseHandler.success(response, "Корзина успешно очищена.")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при очистке корзины.")
      })
    },
    /**
     * Запрос на оформление заказа
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async checkout() {
      const userStore = useUserStore();
      return axios.post(API_URL + `/${userStore.user.id}/checkout`, { headers: userHeader() },)
      .then((response) => {
        this.getCart()
        return ResponseHandler.success(response, "Корзина успешно очищена.")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при оформлении заказа.")
      })
    }
  }
})