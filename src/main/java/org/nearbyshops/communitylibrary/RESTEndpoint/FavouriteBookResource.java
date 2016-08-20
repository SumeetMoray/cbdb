package org.nearbyshops.communitylibrary.RESTEndpoint;

import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.Model.FavouriteBook;
import org.nearbyshops.communitylibrary.ModelEndpoint.FavouriteBookEndpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/FavouriteBook")
public class FavouriteBookResource {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveFavouriteBook(FavouriteBook book)
        {
            int idOfInsertedRow = Globals.favoriteBookDAO.saveBook(book);

            book.setBookID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/FavouriteBook/" + idOfInsertedRow))
                        .entity(book)
                        .build();

                return response;

            }else if(idOfInsertedRow <= 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }


            return null;
        }



        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@QueryParam("BookID")Integer bookID,
                                   @QueryParam("MemberID")Integer memberID)
        {

            int rowCount = Globals.favoriteBookDAO.deleteFavouriteBook(bookID,memberID);


            if(rowCount>=1)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }

            if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getBooks(
                @QueryParam("BookID")Integer bookID,
                @QueryParam("MemberID")Integer memberID,
                @QueryParam("SortBy") String sortBy,
                @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                @QueryParam("metadata_only")Boolean metaonly)
        {

            int set_limit = 30;
            int set_offset = 0;
            final int max_limit = 100;

            if(limit!= null)
            {

                if (limit >= max_limit) {

                    set_limit = max_limit;
                }
                else
                {

                    set_limit = limit;
                }

            }

            if(offset!=null)
            {
                set_offset = offset;
            }

            FavouriteBookEndpoint endPoint = Globals.favoriteBookDAO.getEndPointMetadata(bookID,memberID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<FavouriteBook> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        Globals.favoriteBookDAO.getFavouriteBook(
                                bookID,memberID,
                                sortBy,set_limit,set_offset
                        );

                endPoint.setResults(list);
            }


            //Marker

            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }

}
