<template>
  <v-sheet height="100%" class="d-flex flex-column bg-transparent" rounded>
    <AppHeader/>
    <v-sheet class="d-flex mt-4 flex-row bg-transparent" rounded>
      <v-sheet class="mr-2 bg-transparent" min-width="200px" rounded>
        <v-list rounded nav density="compact" width="100%" v-model="window" elevation="10" class="mb-2">
            <v-list-item
              link
              @click="switchTab('product')"
              :active="window === ProductTab"
              prepend-icon="mdi-package"
              title="Product"/>
              <v-list-item
              link
              @click="switchTab('category')"
              :active="window === CategoryTab"
              prepend-icon="mdi-shape"
              title="Category"/>
        </v-list>
      </v-sheet>
      <v-sheet class="flex-grow-1 bg-transparent" rounded>
        <v-sheet rounded class="bg-transparent">
          <component :is="window"/>
        </v-sheet>
      </v-sheet>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import UserService from "@/services/user.service";
import { onMounted, ref, shallowRef, watch, computed } from "vue";
import AppHeader from "@/components/AppHeader.vue";
import {useUserStore} from "@/stores/userStore";
import {useProductStore} from "@/stores/productStore";
import {useRouter, useRoute} from "vue-router";
import ProductTab from "./tabs/ProductTab.vue";
import CategoryTab from "./tabs/CategoryTab.vue";

const productStore = useProductStore();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();
const window = shallowRef({});
const content = ref("");

// -------------

const loggedIn = computed(() => userStore.status.loggedIn)
const tab = computed(() => route.query.tab)

watch(tab, (value) => {
  window.value = getWindow(value)
})
function getWindow(tab) {
  switch (tab) {
    case 'product': return ProductTab
    case 'category': return CategoryTab
    default: return switchTab('product')
  }
}
function switchTab(tab) {
  router.push({query: {tab}})
}


onMounted(async () => {
  await UserService.getAdminBoard().then(
    (response) => {
      content.value = response.data;
    },
    (error) => {
      content.value =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
    }
  );

  window.value = getWindow(tab.value)
  if (!loggedIn.value) {
    router.push('/user/login');
  }

  productStore.fetchProducts()
})
</script>
