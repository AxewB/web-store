<template>
    <v-sheet 
    height="100vh" 
    class="d-flex 
          justify-center 
          align-center 
          flex-column
          bg-transparent"
    >
      <v-sheet
      class="pa-4 rounded d-flex flex-column"
      width="400px">
      <span class="text-h4 mb-4 text-center">
        Login
      </span>
      <form @submit.prevent="submit">
        <v-text-field
          v-model="username.value.value"
          :error-messages="username.errorMessage.value"
          label="Username"
        ></v-text-field>
        <v-text-field
          v-model="password.value.value"
          :error-messages="password.errorMessage.value"
          type="password"
          label="Password"
        ></v-text-field>
        <v-btn type="submit" variant="tonal" size="large" width="100%">Login</v-btn>
      </form>
      </v-sheet>
      <v-sheet class="pa-4 bg-transparent rounded d-flex flex-row" width="400px">  
        <v-divider class="mt-3 mr-3" />
        OR
        <v-divider class="mt-3 ml-3" />

      </v-sheet>
      <v-sheet 
      class="px-4 bg-transparent rounded d-flex flex-column"
      width="400px">
      <v-btn 
        variant="tonal" 
        size="large" 
        :to="{ path: '/user/register'}">
        Register
      </v-btn>
    </v-sheet>
    </v-sheet>
</template>

<script setup>
import { useField, useForm } from 'vee-validate'
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();

const { handleSubmit } = useForm({
  validationSchema: {
    username (value) {
      if (value?.length >= 2) return true

      return 'Username needs to be at least 2 characters.'
    },
    password (value) {
      if (value?.length > 0) return true

      return 'Password is required.'
    },
  },
})
const username = useField('username')
const password = useField('password')
const message = ref("")
const submit = handleSubmit(user => {
  console.log('user', user)
  userStore.login(user).then(
    () => {
      router.push("user/profile");
    },
    (error) => {
      message.value =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
    }
  );
})
const loggedIn = computed(() => {
    return userStore.user.status.loggedIn;
  },
)
onMounted(() => {
  if (loggedIn.value) {
    router.push('/user/profile');
  }
})
</script>