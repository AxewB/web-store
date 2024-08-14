<template>
    <VSheet
    height="100vh"
    class="d-flex
          justify-center
          align-center
          flex-column
          bg-transparent"
    >
      <VSheet
      class="pa-4 rounded d-flex flex-column"
      width="400px">
      <span class="text-h4 mb-4 text-center">
        Регистрация
      </span>
      <form @submit.prevent="submit">
        <VTextField
          v-model="username.value.value"
          :error-messages="username.errorMessage.value"
          label="Имя пользователя"
        />
        <VTextField
          v-model="email.value.value"
          :error-messages="email.errorMessage.value"
          type="email"
          label="Почта"
        />
        <VTextField
          v-model="password.value.value"
          :error-messages="password.errorMessage.value"
          type="password"
          label="Пароль"
        />
        <VBtn
          type="submit"
          variant="tonal"
          size="large"
          width="100%"
        >
          Register
        </VBtn>
      </form>

      </VSheet>
      <VSheet
        class="pa-4 bg-transparent rounded d-flex flex-row"
        width="400px"
      > 
        <v-divider class="mt-3 mr-3" />
        ИЛИ
        <v-divider class="mt-3 ml-3" />
      </VSheet>
      <VSheet
      class="px-4 bg-transparent rounded d-flex flex-column"
      width="400px"
    >
      <VBtn
        variant="tonal"
        size="large"
        :to="{ path: '/user/login'}"
      >
        Авторизоваться
      </VBtn>
    </VSheet>
  </VSheet>
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
    email (value = "") {
      const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      if (value.match(re)) return true

      return 'Please enter a valid email address.'
    }
  },
})
const username = useField('username')
const password = useField('password')
const email = useField('email')
const message = ref("")
const submit = handleSubmit(user => {
  message.value = "";

  userStore.register(user).then(
    (data) => {
      message.value = data.message;
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
    return userStore.status.loggedIn;
  },
)
onMounted(() => {
  if (loggedIn.value) {
    router.push('/user/profile');
  }
})
</script>