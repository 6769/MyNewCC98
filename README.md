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
use Retrofit to handle http requests, and the interface api.cc98.org should be easiser than html regex paser.

### activity
just reference to old version

### presentation
The WebView is last technology choice to preserent the posts and replys. But the cost is, hard to have scrollinglayout's effects.

Another choice:
UBB code seems not easy to parse in textview so far, but markdown code seems has [RxMarkdown](https://github.com/yydcdut/RxMarkdown) to use.


# Ends
