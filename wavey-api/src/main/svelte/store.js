import { writable } from 'svelte/store'

export const isAuthenticated = writable(localStorage.getItem('token') && localStorage.getItem('token') !== 'undefined')
export const authUserID = writable(localStorage.getItem('authID'))
export const storedJwt = writable(localStorage.getItem('token'))