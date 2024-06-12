<template>
  <div class="login-form-container">
    <form @submit.prevent="handleSubmit">
      <h2>Login</h2>
      <div class="input-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required>
      </div>
      <div class="input-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required>
      </div>
      <button type="submit">Submit</button>
    </form>
  </div>
</template>
  
<script>
export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async handleSubmit() {
      fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: this.email,
          password: this.password,
        })
      }).then(response => {
        if (!response.ok) {
          alert("Los login")
          return;
        }
        return response.json().then(res => {
          console.log(res);
          localStorage.setItem("jwtic", res.jwt);
          this.$router.push('/destinations');

        });
      })
    }
  }
};

</script>
  
<style scoped>
.login-form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffe6f0;
}

form {
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 300px;
}

h2 {
  text-align: center;
  color: #ff69b4;
}

.input-group {
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  color: #ff69b4;
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ff69b4;
  border-radius: 4px;
}

button {
  width: 100%;
  padding: 0.75rem;
  border: none;
  border-radius: 4px;
  background-color: #ff69b4;
  color: white;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background-color: #ff1493;
}
</style>
  