import authHeader from "./auth-header"
export default function contentHeader() {
  return {
    'Content-Type': 'application/json',
    ...authHeader(),
  }
}