import axios from 'axios';
import authHeader from '@/services/auth-header';
import { useUserStore } from './userStore';
import { defineStore } from 'pinia';
import ResponseHandler from '@/scripts/responseHandler';
const API_URL = "http://localhost:8080/api/orders";


export const useOrderStore = defineStore('order', {
  state: () => ({
    orders: [],
  }),

  actions: {
    /**
     * Запрос на получение списка заказов.
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchOrders() {
      const userStore = useUserStore();
      return axios.get(API_URL + `/user/${userStore.user.id}`, { headers: authHeader() })
      .then(response => {
        this.orders = response.data
        return ResponseHandler.success(response, "Заказы получены")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при получении заказов")
      });
    },
    /**
     *
     * @param {Number} orderId - id заказа
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchOrder(orderId) {
      return axios.get(API_URL + `/${orderId}`, { headers: authHeader() })
      .then(response => {
        return ResponseHandler.success(response, "Заказ получен") // TODO: может быть тут возникнет ошибка, но ваще хз зачем мне этот метод
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при получении заказа")
      });
    }
  }
})
