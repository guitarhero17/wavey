<script>
  import { onMount } from 'svelte';
  import { fetchWithJwt } from '../lib/userAuthentication'
  import { retrieveAuthUserId } from '../lib/userAuthentication'
  export let userId
  export let waveId

let clapsCount = 0
let heartsCount = 0
let likesCount = 0

let data = null

data = fetchWithJwt(
  `https://localhost:8443/users/${userId}/waves/${waveId}/reactions`
  )
  .then((res) => res._embedded)
  .then((resEmb) => resEmb.waveReactions)


onMount(async () => {
  let reactions = await data

  clapsCount = countReactions(reactions, 'CLAP')
  heartsCount = countReactions(reactions, 'LOVE')
  likesCount = countReactions(reactions, 'LIKE')
})

const countReactions = (reactions, type) => {
  return reactions.map(entry => entry.reaction).filter(entry => entry == type).length
}

const incrementReaction = react => {
  // console.log('Incrementing reaction '+ react)
  const reqBody = {
    date: Date.now(),
    reaction: react
  }
  fetchWithJwt(`https://localhost:8443/users/${userId}/waves/${waveId}/reactions`, 'POST', reqBody)
  .then((resJson) => {
    if(resJson.id && resJson.reaction === react) {
      switch(react) {
        case 'CLAP': {
          clapsCount += 1
          return
        }
        case 'LOVE': {
          heartsCount += 1
          return
        }
        case 'LIKE': {
          likesCount += 1
          return
        }
      }
    }
  })
}

const incrementClap = () => {
  if (userId != retrieveAuthUserId()) {
    incrementReaction('CLAP')
  }
}
const incrementLike = () => {
  if (userId != retrieveAuthUserId()) {
    incrementReaction('LIKE')
  }
}
const incrementHeart = () => {
  if (userId != retrieveAuthUserId()) {
    incrementReaction('LOVE')
  }
}
</script>

<style>
  .reaction-wrapper {
    @apply transform;
    @apply transition-transform;
    @apply cursor-pointer; 
  }

  .reaction-wrapper:hover {
    @apply scale-110;
  }

  .reaction-wrapper.disabled {
    opacity: 0.5;
    cursor: default;
  }

  .reaction-wrapper.disabled:hover {
    @apply scale-100;
  }
</style>

{#await data}
  <p> Reactions coming up...</p>
{:then reactions}
  <div class="reaction-container py-4 ml-6 flex justify-around items-center rounded-xl w-40 border border-waveyGreen">
      <div class:disabled= { userId == retrieveAuthUserId() } class="clap-wrapper reaction-wrapper" on:click= { incrementClap }>
        <div class="clap-icon text-2xl">👏</div>
        <div class="clap-count text-center">{ clapsCount }</div>
      </div>
      <div class:disabled= { userId == retrieveAuthUserId() } class="like-wrapper reaction-wrapper" on:click= { incrementLike }>
        <div class="like-icon text-2xl">👍</div>
        <div class="like-count text-center">{ likesCount }</div>
      </div>
      <div class:disabled= { userId == retrieveAuthUserId() } class="heart-wrapper reaction-wrapper" on:click= { incrementHeart }>
        <div class="heart-icon text-2xl">❤️</div>
        <div class="heart-count text-center">{ heartsCount }</div>
      </div>
  </div>
{:catch error}
    <p>{error}</p>
{/await}