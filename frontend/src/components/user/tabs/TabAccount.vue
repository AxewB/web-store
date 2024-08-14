<template>
  <VSheet
    v-if="userData"
    class="pa-2 d-flex flex-column"
    rounded
  >
    <span class="text-h5 mb-2 font-weight-bold">
      Данные об аккаунте
    </span>
    <span class="text-h6 mb-2 font-weight-bold">
      Страница без полезной информации. Для заполнения решено было оставить данные пользователя:
    </span>
    <VSheet class="pa-4">
      <VTextField
        v-for="key in Object.keys(userData)"
        class="mb-2"
        :label="key"
        v-model="userData[key]"
        hide-details
        variant="plain"
        readonly
      />
    </VSheet>
  </VSheet>
</template>

<script setup>
import {computed, ref} from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const userData = computed(() => {
  return userStore.user
})
</script>
