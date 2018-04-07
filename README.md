### API GmailLogin 
The api GmailLogin library use for gmail integration in android app and in this library 10.0.1 version of Google Play lib is used.
 * if gmail login is successfull using this library then GoogleSignInAccount is pass to the calling activity in the RESULT_OK result code.
 * object of googleSignInAccount is used for getting user account info (details of user).
 * If the failure occur during login then result code is RESULT_CANCELED pass to the calling activitty.
 * The reason of failure is may be not generated configuration file or wrong client id is pass to the intent.

#### Impelementation steps

 - Copy folder in the project root directory.</br>
 for example </br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Project_Nam/GmailLogin  
- Now go in the <span style="color:green">settings.gradle</span> file and replace 

```sh
include ':app'
```
with 

```sh
include ':app',':gmailLogin'
```

- Open app's <span style="color:green">build.gradle</span> file and add the <span style="color:blue">compile project <span style="color:green">(path:':logger')</span></span> line in depencies module such as : </br> 

```sh
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.1.0'
    compile 'com.android.support:design:25.1.0'
    compile project(path: ':gmailLogin')

}
```
#### How to use this library for login and logout with gmail in app

#### Step 1
 - get Configuration file from developer console 
 - copy this configration file in app folder  

#### Step 2

#### For do login with gmail :

- for gmail login using this library user call startActivityForResult(intent,request_code)
- sample for making intent of gmail login
- if user Require the tokenId also then user must also pass the valid client id provided by google console
- client id is web-client present in google console or present in configuration file
	

#### How to make intent
	- If required tokenID also with basic profile information of user
	- Then make intent like following
	
```sh
	    Intent gmailLogin = new Intent(SampleActivity.this, GmailLogin.class);
        gmailActivity.putExtra(GmailConstant.CLIENT_ID, "905719263858-5lirdltnghncss2qmh0nvudeps1af50s.apps.googleusercontent.com");
        gmailActivity.putExtra(GmailConstant.ACTION,GmailConstant.LOG_IN);
        startActivityForResult(gmailActivity, gmail_request);
```
#### Or 
	- if required only basic profile information
	- Then make Intent like following
	
```sh
        Intent gmailLogin = new Intent(SampleActivity.this, GmailLogin.class);
        gmailActivity.putExtra(GmailConstant.ACTION,GmailConstant.LOG_IN);
        startActivityForResult(gmailActivity, gmail_request);
```
	
#### Do changes in the onActivityResult for gmail login	
	- token id is null if you don't pass client ID in intent
        
```sh
    if (requestCode == gmail_request) {
            if (resultCode == Activity.RESULT_OK) {
                GoogleSignInAccount googleSignInAccount = data.getParcelableExtra(GmailConstant.ACCOUNT_INFO);
                Log.d(TAG, "accont info is  " + googleSignInAccount.getDisplayName() + "  email " + googleSignInAccount.getEmail()
                        + " id token  " + googleSignInAccount.getIdToken() + "  photo url " + googleSignInAccount.getPhotoUrl() + " family name   " + googleSignInAccount.getFamilyName()
                        + "  getAccount " + googleSignInAccount.getAccount() + " get ID " + googleSignInAccount.getId());

            }
            else
                Log.d(TAG, "app is unauthenticate or client id you pass is invalid");
        } 
```
#### Step 3

#### For logout from gmail :

- for gmail logout using this library user call startActivityForResult(intent,request_code)
- sample for making intent of gmail logout shown beow


#### How to make intent

```sh
        Intent gmailLogout = new Intent(SampleActivity.this, GmailLogin.class);
        gmailActivity.putExtra(GmailConstant.ACTION, GmailConstant.LOG_OUT);
        startActivityForResult(gmailActivity, gmail_log_out_request);
```
#### Do changes in the onActivityResult for gmail logout
```sh
    if (requestCode == gmail_log_out_reqquest) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "logout from gmail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "problem during logout", Toast.LENGTH_SHORT).show();
            }
        }
```

