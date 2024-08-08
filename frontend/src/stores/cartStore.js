import {defineStore} from 'pinia';
import { useUserStore } from '@/stores/userStore';
import contentHeader from '@/services/content-header';
import userHeader from '@/services/auth-header';
import axios from 'axios';

const API_URL = "http://localhost:8080/api/cart";

export const useCartStore = defineStore('cart', {
  state: () => ({
    cart: [],
    totalCost: 0,
  }),

  getters: {
    isProductInCart: (state) => {
      return (product) => {
        return state.cart.products.some((item) => item.id === product.id);
      }
    }
  },

  actions: {
    async getCart() {
      const userStore = useUserStore();
      axios.get(API_URL + `/${userStore.user.id}`, { headers: userHeader() })
      .then((response) => {
        this.cart = response.data.cart
        console.log(this.cart)
        this.totalCost = response.data.totalCost
      });
    },
    async addToCart(product) {
      const userStore = useUserStore();
      const { id } = product;
      axios.post(API_URL + `/${userStore.user.id}/add?productId=${id}`, { headers: contentHeader() })
      .then(() => this.getCart())
    },
    async removeFromCart(product) {
      const userStore = useUserStore();
      const { id } = product;
      axios.delete(API_URL + `/${userStore.user.id}/remove?productId=${id}`, { headers: userHeader() })
      .then(() => this.getCart())
      .catch((error) => console.log(error))
    },
    async removeFromCartById(productId) {
      const userStore = useUserStore();
      const id = productId;
      axios.delete(API_URL + `/${userStore.user.id}/remove?productId=${id}`, { headers: userHeader() })
      .then(() => this.getCart())
      .catch((error) => console.log(error))
    },
    async clearCart() {
      const userStore = useUserStore();
      axios.delete(API_URL + `/${userStore.user.id}/clear`, { headers: userHeader() })
      .then(() => this.getCart())
    },
    async checkout() {
      const userStore = useUserStore();
      axios.post(API_URL + `/${userStore.user.id}/checkout`, { headers: userHeader() },)
      .then(() => this.getCart())
    }
  }
})