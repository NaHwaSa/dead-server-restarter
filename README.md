# dead-server-restarter
특정 서버에 대해 코드를 확인할 수 없어 원본적인 해결은 안되지만, 아무튼 자꾸 죽을 경우 주기적으로 죽었는지 확인해서 서비스를 재실행이라도 시키기 위한 임시방편용 프로그램.
해당 서버를 체크하고 있다가, 죽었을 시 원하는 bat, sh를 실행해주는 역할을 함.

**[설정]**
1. Config 클래스에 LIVE_CHECK_URL에 해당 서버 get 방식으로 200 ok 체크 할 수 있는 URL 입력 (클라우드 로드밸런서 상태체크 URL 등)
2. FILE_FOR_EACH_RESTART_PROCESS_NUM에 여러개의 기본 세팅 저장(윈도우-bat, 리눅스-sh 등)
3. RESTART_PROCESS_NUM에 '2'에서 설정한것 중 실제 동작을 뭘로할지 배열 인덱스 기입
4. '2'에 설정해둔 파일 내에 해당 서버가 죽었을 시 재실행할 수 있는 명령어 작성.
- e.g. 윈도우에서 톰캣을 통해 실행된 서버의 경우 (.bat)
```
@echo off

echo --RESTART BATCH--
set "TOMCAT_PATH=C:\apache_tomcat_1"
set "STOP=%TOMCAT_PATH%\bin\shutdown.bat"
set "START=%TOMCAT_PATH%\bin\startup.bat"

@echo on
call %STOP%
timeout /t 2
call %START%
timeout /t 2
```
- e.g. 윈도우에서 윈도우 서비스에 등록된 서버의 경우 (.bat)
```
net stop [Service name]
timeout /t 2
net start [Service name]
timeout /t 2
```
- e.g. 리눅스에서 pm2를 사용해 올려둔 리액트 노드 서버 (.sh)
```
pm2 stop 0
pm2 serve build 3000 --spa
```
5. DELAY와 PERIOD 설정. [delay]ms가 지난 후 [period]ms 간격으로 체크함.

***
**[빌드]**
```
./gradlew build jar
```
***
**[실행]**
```
java -jar [파일명].jar
```
***
**[주의점]**
* jdk 11 이상 필요.
* 중간에 키입력을 통한 비밀번호 입력이 필요한 bat 혹은 sh 파일은 실행이 당연히 안됨.
  * 윈도우에서 실행 시 java -jar... 을 실행할 cmd 혹은 powershell은 관리자권한으로 작동시킬 것. (중간에 비밀번호를 물어보면 안되기 때문.)
  * 관리자 권한으로 실행해도 비밀번호를 물어본다면 runas 명령어를 활용해서 bat 파일을 작성할 것.
  * 마찬가지로 리눅스에서 중간에 비밀번호가 필요한 경우 expect 등을 사용해서 sh 파일을 작성할 것.
