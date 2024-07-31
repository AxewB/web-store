<template>
  <v-sheet v-if="userData" class="pa-2 d-flex flex-column" rounded>
    <span class="text-h5 mb-2 font-weight-bold">Account data</span>
    <v-sheet >
      <v-text-field 
        v-for="key in Object.keys(userData)" 
        :label="key" 
        :value="userData[key]"
      />
      <VDivider/>
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import {computed, ref} from 'vue';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();

const userData = computed(() => {
  return userStore.user
})
</script>
