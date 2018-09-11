/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alway.lequ_kotlin.net.parser

import com.alway.lequ_kotlin.net.RestCretor
import okhttp3.HttpUrl

/**
 * ================================================
 * 默认解析器, 可根据自定义策略选择不同的解析器
 *
 *
 * 如果您觉得 [DefaultUrlParser] 的解析策略并不能满足您的需求, 您可以自行实现更适合您的 [UrlParser]
 * 然后通过 [RetrofitUrlManager.setUrlParser] 配置给框架, 即可替换 [DefaultUrlParser]
 * 自己改 [DefaultUrlParser] 的源码来达到扩展的目的是很笨的行为
 *
 * @see UrlParser
 * Created by JessYan on 17/07/2017 18:23  采用jessYan动态url更替方案
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class DefaultUrlParser : UrlParser {

    private var mDomainUrlParser: UrlParser? = null
    private var mRestCretor: RestCretor? = null

    override fun init(restCretor: RestCretor) : UrlParser {
        this.mRestCretor = restCretor
        this.mDomainUrlParser = DomainUrlParser()
        this.mDomainUrlParser = DomainUrlParser().init(restCretor)
        return this
    }

    override fun parseUrl(domainUrl: HttpUrl?, url: HttpUrl): HttpUrl {
        if (null == domainUrl) return url
        return mDomainUrlParser!!.parseUrl(domainUrl, url)
    }
}
