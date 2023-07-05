<style global>
  @tailwind base;
  @tailwind components;
  @tailwind utilities;

  * {
    z-index: 1;
  }

  @font-face {
    font-family: 'Glacial Indifference';
    src: local('Glacial Indifference'), url('public/fonts/GlacialIndifference-Regular.woff') format('woff');
  }
  @font-face {
    font-family: 'Glacial Indifference Bold';
    src: local('Glacial Indifference Bold'), url('public/fonts/GlacialIndifference-Bold.woff') format('woff');
  }

  .waves-bg-animated {
    position: relative;
    overflow: hidden;
    background: none;
  }
  .waves-bg-animated::before {
    content: "";
    position: absolute;
    width: 150%;
    height: 150%;
    top: 0;
    left: -50%;
    z-index: -1;
    transform: rotate(-30deg);
    background-color: #000000;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='18' viewBox='0 0 100 18'%3E%3Cpath fill='%2337c9b7' fill-opacity='0.6' d='M61.82 18c3.47-1.45 6.86-3.78 11.3-7.34C78 6.76 80.34 5.1 83.87 3.42 88.56 1.16 93.75 0 100 0v6.16C98.76 6.05 97.43 6 96 6c-9.59 0-14.23 2.23-23.13 9.34-1.28 1.03-2.39 1.9-3.4 2.66h-7.65zm-23.64 0H22.52c-1-.76-2.1-1.63-3.4-2.66C11.57 9.3 7.08 6.78 0 6.16V0c6.25 0 11.44 1.16 16.14 3.42 3.53 1.7 5.87 3.35 10.73 7.24 4.45 3.56 7.84 5.9 11.31 7.34zM61.82 0h7.66a39.57 39.57 0 0 1-7.34 4.58C57.44 6.84 52.25 8 46 8S34.56 6.84 29.86 4.58A39.57 39.57 0 0 1 22.52 0h15.66C41.65 1.44 45.21 2 50 2c4.8 0 8.35-.56 11.82-2z'%3E%3C/path%3E%3C/svg%3E");
    animation: animateBackground 20s linear infinite;
    animation-fill-mode: forwards;
    animation-direction: alternate;
    animation-play-state: paused;
  }

  .waves-bg-animated:hover::before {
    animation-play-state: running;
  }

  @keyframes animateBackground {
    from {
      background-position: 0 0;
    }
    to {
      background-position: 100% 0;
    }
  }

  .window[role="dialog"] {
    @apply bg-gray-900;
    @apply text-white;
    @apply rounded-3xl;
  }

  .window[role="dialog"] .close:hover {
    @apply bg-waveyYellow;
  }

  .window[role="dialog"] .close:hover::before, .window[role="dialog"] .close:hover::after {
    @apply bg-black;
  }

  .window[role="dialog"] .content {
    @apply pt-16;
    @apply px-16;
    @apply h-96;
    overflow: hidden;
  }

  @media screen and (max-width: 768px) {
    .window[role="dialog"] .content {
      @apply pt-10;
      @apply px-10;
      height: 30rem;
    }
  }

  .window[role="dialog"] .content h2{
    @apply text-4xl;
    @apply text-center;
  }

  .window[role="dialog"] .content p {
    @apply text-center;
  }

  .window[role="dialog"] .content .sveltejs-forms {
    @apply mt-7;
    @apply text-center;
  }

  .window[role="dialog"] .content .sveltejs-forms .field:first-child {
    @apply mt-2;
  }

  .window[role="dialog"] .content .sveltejs-forms .field {
    @apply mt-1;
  }

  .window[role="dialog"] .content .sveltejs-forms .field input {
    @apply text-center;
    @apply rounded-md;
    color: black;
  }

  .window[role="dialog"] .content .login-side .sveltejs-forms .field.error .message {
    @apply text-waveyYellow;
    @apply mt-2;
  }

  .window[role="dialog"] .content .login-side .sign-up-prompt {
    @apply flex;
    @apply justify-center;
    @apply items-center;
    @apply mt-4;
  }

  .window[role="dialog"] .content .signup-side {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    transform: translateX(100%);
    transition: transform 600ms ease-out;
    z-index: 2;
    @apply bg-gray-900;
  }

  .window[role="dialog"] .content .signup-side.is-open {
    transform: translateX(0%);
  }

  .window[role="dialog"] .content .signup-side #go-back {
    position: absolute;
    top: 10px;
    left: 10px;
    width: 40px;
    height: 40px;
    background: url("/images/svg/back-arrow.svg") no-repeat center;
    background-size: contain;
    cursor: pointer;
  }

  .window[role="dialog"] .content .signup-side .sveltejs-forms {
    text-align: center;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    height: 70%;
  }

  .window[role="dialog"] .content .signup-side .sveltejs-forms label{
    display: block;
    @apply mb-3;
  }
  
  .inline-content-loader svg{
    display: inline-block;
  }

</style>

<script>
	import { Router } from '@roxi/routify'
	import { routes } from '../.routify/routes.js'
</script>

<Router {routes} />
<!--<p>Outside of Routify, but still in App.svelte</p>-->