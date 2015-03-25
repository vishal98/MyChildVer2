package ghumover2

import grails.converters.JSON
import grails.rest.RestfulController
import grails.plugin.springsecurity.annotation.Secured;

@Secured(['ROLE_PARENT'])
class GuardianController extends RestfulController
{

    static responseFormats = ['json', 'xml']

    GuardianController() {
        super(Guardian)
    }


    def getAccountInfo()
    {
         def id = params.id.toString()

         def response = (id.isNumber()) ? Guardian.findById(id) : Guardian.findByUsername(id);
         render response as JSON

    }



}