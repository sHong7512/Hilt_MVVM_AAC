# Hilt_MVVM_AAC

Hilt DI example for MVVM_AAC by sHong
 
# Annotations

@HiltAndroidApp
- 앱 최상단 클래스(Application)에서 Hilt를 사용(선언)하기 위해 선언함

@AndroidEntryPoint
- 의존성 주입을 받을 클래스에 선언하는 annotation
- Fragment에 주입시에는 상위에 있는 Activity에도 선언해줘야함

@Inject 
- 객체를 주입을 받을때 사용 
``` 
(ex)@inject lateinit var dateFormatter 
```
- 주입하는 객체에 constructor와 함께 사용 
``` 
(ex) class repository @inject constructor(){} 
```

@InstallIn(SingletoneComponent::class)
- 애플리케이션 컨테이너에 연결된 Hilt 구성요소 클래스 ```(ex) SingletoneComponent::class 를 전달```

@Singleton
- 싱글톤으로 객체를 생성해주는 스코프(범위 Application) ```android guide 링크 참고```

@Binds
- 추상함수(interface, abstract class 등등)를 DI하기 위해서 사용함

@EntryPoint
- Hilt에서 지원하지 않는 클래스에 종속 항목을 삽입하기 위해 사용

@HiltAndroidTest
- Hilt 테스트를 하기위해 선언
```
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest

class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    ...
    
}
```

# 같은 형식의 모듈 선언

- Hilt의 DI는 형식(ex String, Int….etc)을 보고 주입을 해주기 때문에, 같은 형식의 모듈을 생성할 시 커스텀 Annotation을 붙여줘야한다.
(ex)
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppHash

- 제공하는 쪽, 제공 받는 쪽 둘다 어노테이션을 붙여줘야 한다.
(ex)
@AppHash
@Inject
lateinit var hash : String

# Hilt를 ViewModel에 연동

- @ViewModelInject 를 사용해준다.
(ex) class MainViewModel @ViewModelInject constructor(
	private repository: Repository
): ViewModel(){ }


# Reference

- 힐트 가이드

https://developer.android.com/codelabs/android-hilt?hl=ko#0

- annotations

https://developer.android.com/training/dependency-injection/hilt-android

- 같은 형식의 모듈 선언

https://developer.android.com/training/dependency-injection/hilt-multi-module

- Hilt with jetpack

https://developer.android.com/training/dependency-injection/hilt-jetpack

- 설명

https://medium.com/@jang.wangsu/di-dependency-injection-%EC%9D%B4%EB%9E%80-1b12fdefec4f

- 굳이 DI 라이브러리를 사용하는 이유

https://nuritech.tistory.com/8
