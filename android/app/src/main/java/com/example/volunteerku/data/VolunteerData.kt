package com.example.volunteerku.data

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name = "header")
data class header (
    @PropertyElement(name="resultCode")
    var resultCode: Int = 0,

    @PropertyElement(name="resultMsg")
    var resultMsg: String? = null
)

@Xml(name = "body")
data class body (
    @Element(name = "items")
    var items: items? = null,

    @PropertyElement(name="numOfRows")
    var numOfRows: Int = 0,

    @PropertyElement(name="pageNo")
    var pageNo: Int = 0,

    @PropertyElement(name="totalCount")
    var totalCount: Int = 0
)

@Xml(name = "response")
data class response (
    @Element(name = "header")
    var header: header? = null,

    @Element(name = "body")
    var body: body? = null
)


@Xml(name = "items")
data class items(
    @Element(name = "item")
    var item: List<item>? = null
)

@Xml(name= "item")
data class item(

    @PropertyElement(name="url")
    var url: String? = "",

    @PropertyElement(name="actBeginTm")
var actBeginTm: String? = "",
@PropertyElement(name="actEndTm")
var actEndTm: String? = "",
@PropertyElement(name="actPlace")
var actPlace: String? = "",
@PropertyElement(name="actWkdy")
var actWkdy: String? = "",
@PropertyElement(name="adultPosblAt")
var adultPosblAt: String? = "",
@PropertyElement(name="appTotal")
var appTotal: String? = "",
@PropertyElement(name="areaAddress1")
var areaAddress1: String? = "",
@PropertyElement(name="areaAddress2")
var areaAddress2: String? = "",
@PropertyElement(name="areaAddress3")
var areaAddress3: String? = "",
@PropertyElement(name="areaLalo1")
var areaLalo1: String? = "",
@PropertyElement(name="areaLalo2")
var areaLalo2: String? = "",
@PropertyElement(name="areaLalo3")
var areaLalo3: String? = "",
@PropertyElement(name="email")
var email: String? = "",
@PropertyElement(name="familyPosblAt")
var familyPosblAt: String? = "",
@PropertyElement(name="fxnum")
var fxnum: String? = "",
@PropertyElement(name="grpPosblAt")
var grpPosblAt: String? = "",
@PropertyElement(name="gugunCd")
var gugunCd: String? = "",
@PropertyElement(name="mnnstNm")
var mnnstNm: String? = "",
@PropertyElement(name="nanmmbyNm")
var nanmmbyNm: String? = "",
@PropertyElement(name="nanmmbyNmAdmn")
var nanmmbyNmAdmn: String? = "",
@PropertyElement(name="noticeBgnde")
var noticeBgnde: String? = "",
@PropertyElement(name="noticeEndde")
var noticeEndde: String? = "",
@PropertyElement(name="pbsvntPosblAt")
var pbsvntPosblAt: String? = "",
@PropertyElement(name="postAdres")
var postAdres: String? = "",
@PropertyElement(name="progrmBgnde")
var progrmBgnde: String? = "",
@PropertyElement(name="progrmCn")
var progrmCn: String? = "",
@PropertyElement(name="progrmEndde")
var progrmEndde: String? = "",
@PropertyElement(name="progrmRegistNo")
var progrmRegistNo: String? = "",
@PropertyElement(name="progrmSj")
var progrmSj: String? = "",
@PropertyElement(name="progrmSttusSe")
var progrmSttusSe: String? = "",
@PropertyElement(name="rcritNmpr")
var rcritNmpr: String? = "",
@PropertyElement(name="sidoCd")
var sidoCd: String? = "",
@PropertyElement(name="srvcClCode")
var srvcClCode: String? = "",
@PropertyElement(name="telno")
var telno: String? = "",
@PropertyElement(name="yngbgsPosblAt")
var yngbgsPosblAt: String? = ""
)

