# MyNewCC98
Experimental: new cc98 client

## Structure
The user triggers some observable,the RxJava's Observer start his execution and reply to caller.

`signal->process->presentation`

major view parts:

board view, post view, post content view;
pm view, search view, user view;

## Modules

### network
use Retrofit to handle http requests, and the interface api-v2.cc98.org should be easiser than html regex paser.

#### Authentication OAth
Apply web's authentication key and token way.

### activity
just reference to old version

### presentation
The WebView is last technology choice to preserent the posts and replys. But the cost is, hard to have scrollinglayout's effects.

Another choice:
UBB code seems not easy to parse in textview so far, but markdown code seems has [RxMarkdown](https://github.com/yydcdut/RxMarkdown) to use.

#### UserProfile
Directly load into webview;

#### PMView/Reply
similar to topic view,their render system are the same;

#### TopicView
Use react and Typescript to write front part.

## Utilities
Try to include some handy toolkits:
- Share Topic to Another App. Done.
- PhotoView Save to SDCard.   Doing...
- Topic to ScreenShot.
- Funny emotion stickers.

# How to Compile
Under AndroidStudio 3.0.1 , Gradle 4.1;

be careful about the submodule git repos;
```
 git submodule update --init --recursive
 
 git clone https://github.com/6769/MyNewCC98.git  --recursive
```
