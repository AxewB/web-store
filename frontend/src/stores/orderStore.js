import axios from 'axios';
import authHeader from '@/services/auth-header';
import { useUserStore } from './userStore';
import { defineStore } from 'pinia';
const API_URL = "http://localhost:8080/api/orders";


export const useOrderStore = defineStore('order', {
  state: () => ({
    orders: [],
  }),

  actions: {
    async fetchOrders() {
      const userStore = useUserStore();
      await axios.get(API_URL + `/user/${userStore.user.id}`, { headers: authHeader() })
      .then(response => this.orders = response.data)
      .catch(error => console.log(error));
    },
    async fetchOrder(orderId) {
      await axios.get(API_URL + `/${orderId}`, { headers: authHeader() })
      .then(response => response.order)
      .catch(error => console.log(error));
    }
  }
})
