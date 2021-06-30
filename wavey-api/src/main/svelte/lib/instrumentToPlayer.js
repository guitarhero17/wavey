export default function convertInstrumentToPlayerType(instrument) {
  let playerType
  switch (instrument) {
    case 'DRUMS': {
      playerType = 'Drummers'
      break
    }
    case 'GUITAR': {
      playerType = 'Guitarists'
      break
    }
    case 'BASS': {
      playerType = 'Bassists'
      break
    }
    case 'KEYS': {
      playerType = 'Keyboard players'
      break
    }
    case 'VOCALS': {
      playerType = 'Singers'
      break
    }
    case 'CELLO': {
      playerType = 'Cellists'
      break
    }
    case 'SAXOPHONE': {
      playerType = 'Saxophonists'
      break
    }
    case 'TRUMPET': {
      playerType = 'Trumpeters'
      break
    }
    case 'VIOLIN': {
      playerType = 'Violinists'
      break
    }
    default :{
      playerType = 'Some players to jam with'
      break
    }
  }
  return playerType
}