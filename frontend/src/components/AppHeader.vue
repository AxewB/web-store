<template>
  <v-sheet width="100%" class="bg-transparent">
    <v-toolbar density="default" class="d-flex align-center pa-2 rounded" width="100%">
      <ShopLogo />

      <v-btn
        prepend-icon="mdi-menu"
        size="large"
        variant="flat"
        class="mx-4"
        color="primary">
        Каталог
      </v-btn>
      <v-text-field
        label="Search"
        hide-details
        variant="solo"
        class="mx-2">
        <template #append-inner>
          <v-btn
            color="primary"
            append-icon="mdi-magnify"
            variant="tonal"
          >
            ИСКАТЬ
          </v-btn>
        </template>
      </v-text-field>

      <v-btn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-account"
        stacked
        :to="{ path: '/user/profile' }"
      >
        Профиль
      </v-btn>
      <v-btn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-package-variant"
        stacked
      >
        Заказы
      </v-btn>
      <v-btn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-cart"
        stacked
        :to="{ path: '/cart' }"
      >
        Корзина
      </v-btn>
    </v-toolbar>
  </v-sheet>
</template>

<script setup>
import ShopLogo from './ShopLogo.vue';
import { useRouter } from 'vue-router';

const onSearchClick = () => {
  router.push({name: "productSearch"})
}
</script>
