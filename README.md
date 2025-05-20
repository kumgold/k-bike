# K-자전거
## 설명
원하는 위치를 검색하여 Google Map 위에 마커 표시할 수 있고 주변 시설에 대한 정보를 얻을 수 있습니다. 마커 표시된 주소는 저장 및 삭제가 가능합니다.
Naver geocoding API를 활용하여 네비게이션을 기능을 사용할 수 있습니다. 주소 검색을 통해서 해당 위치 주변 시설 파악, 마커 클릭을 통해 해당 위치 정보 검색 같은 기능을 구현했습니다.

## 화면
| Home                                    | Write                                    |
|-----------------------------------------|------------------------------------------|
| <img src="./images/main.png" width=150> | <img src="./images/search_address.png" width=150> |

## 구현 기능
- Google Map, GeoCoding을 구현하여 지도 위에 마커 표시 기능을 사용할 수 있습니다.
- Naver Navigation을 구현하여 From.(주소) - To.(주소)에 대한 Navigation Path를 지도 위에서 확인할 수 있습니다.
- Kakao Search(WebView) API를 구현하여 마커를 클릭하면 WebView에 해당 주소에 대한 Daum 검색이 활성화 됩니다.

## 기술 스택
Language : Kotlin <br>
View : XML, Compose <br>
AndroidX : Room, ViewModel, Hilt, WebView <br>
Kotlin : Coroutine, StateFlow <br>
etc : Retrofit, OkHttp, Google Map, Kakao Address <br>

