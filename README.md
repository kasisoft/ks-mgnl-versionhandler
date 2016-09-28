Purpose
=======

'''NOTE:''' This documentation is currently work-in-progress and therefore incomplete. 28-Sep-2016

Magnolia module providing an alternative solution in order to setup jcr configurations.


Contact
-------

* daniel.kasmeroglu@kasisoft.net


Requirements
------------

 * Java 8
 * Magnolia 5.4.9


Development Setup
-----------------

* Lombok is used, so check it out: https://projectlombok.org/


Jenkins (CI)
------------

* https://kasisoft.com/ci/job/mgnl.versionhandler


Maven
-----

     <dependency>
         <groupId>com.kasisoft.mgnl</groupId>
         <artifactId>com.kasisoft.mgnl.versionhandler</artifactId>
         <version>0.1-SNAPSHOT</version>
     </dependency>
     
     <repositories>
         <repository>
             <id>kasisoft</id>
             <url>https://kasisoft.com/artifactory/remote-repos</url>
             <releases>
                 <enabled>true</enabled>
             </releases>
             <snapshots>
                 <enabled>true</enabled>
             </snapshots>
         </repository>
     </repositories>
     
     
Basic scenario
--------------

    public CustomThemeVersionHandler() {
        register( 1, new VirtualUriMappingTask() );
        register( 1, "team-alpha", new AlphaMappingTask() );
        register( 2, new ErrorPageMappingTasks() );
    }
    
This causes the setup of the following properties within the module:

 * update_default     - if everything succeeds -> property will get the value 2
 * update_team-alpha  - if everything succeeds -> property will get the value 1


Tasks may depend on the *JcrConfigurationTask* which allows to register structures like this:

    public class TemplatesTask extends JcrConfigurationTask {
    
      public TemplatesTask() {
        super( "Templates installation", "install templates" );
        register( RepositoryConstants.CONFIG, newTemplatesTree() );
      }
      
      private TreeBuilder newTemplatesTree() {
        return new TreeBuilder()
          .sNode( "modules" )
            .sFolder( "templating" )
              .sFolder( "templates" )
                .sFolder( "pages" )
                  .sContentNode( "main" )
                    .yaml( "templating/templates/pages/main.yaml" )
                  .sEnd()
                .sEnd()
              .sEnd()
            .sEnd()
          .sEnd()
          ;
      }
    
    } /* ENDCLASS */

This task will grant node *main* below the path */modules/templating/templates/pages* within the *CONFIG* workspace. The
content of the node will be taken from the YAML resource *templating/templates/pages/main.yaml* which must be located on
the classpath (just an example). 
You can add properties directly, too.
A final sequence of *sEnd()* calls is allowed to be omitted, so the tree could be written like this as well:

    private TreeBuilder newTemplatesTree() {
      return new TreeBuilder()
        .sNode( "modules" )
          .sFolder( "templating" )
            .sFolder( "templates" )
              .sFolder( "pages" )
                .sContentNode( "main" )
                  .yaml( "templating/templates/pages/main.yaml" )
          ;
    }
    

License
-------

* BSD: http://directory.fsf.org/wiki/License:BSD_3Clause

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    (1) Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer. 

    (2) Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in
    the documentation and/or other materials provided with the
    distribution.  
    
    (3)The name of the author may not be used to
    endorse or promote products derived from this software without
    specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.