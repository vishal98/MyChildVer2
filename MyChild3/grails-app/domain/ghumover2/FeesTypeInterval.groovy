package ghumover2

import grails.rest.Resource

@Resource(formats=['json', 'xml'])
class FeesTypeInterval {

    Integer feesTypeInterval_Id
    String feesTypeInterval
    static mapping = { id generator: 'increment',name: 'feesTypeInterval_Id'}
    static constraints = {
    }
}
