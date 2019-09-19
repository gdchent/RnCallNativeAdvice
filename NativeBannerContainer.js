import React from 'react'
import { requireNativeComponent, View,Dimensions  } from 'react-native'
import PropTypes from 'prop-types'

export const { width, height } = Dimensions.get('window')
const AndroidBannerContainer= requireNativeComponent('BannerContainer', NativeBannerContainer)
class NativeBannerContainer extends React.PureComponent{

    static propTypes = {
      
    }

    render(){
        return (
            <AndroidBannerContainer
                style={{ width: 100, height: 100 }}
             
            />
        )
    }
}

export default NativeBannerContainer