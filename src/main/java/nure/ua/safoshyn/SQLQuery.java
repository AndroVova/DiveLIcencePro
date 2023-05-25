package nure.ua.safoshyn;

public class SQLQuery {

    static public final String GET_CERTIFICATES_BY_DIVE_CLUB_ID = "FROM Certificate c " +
            "LEFT JOIN CustomUser cu ON cu.id = c.customUser.id " +
            "LEFT JOIN DiveClub dc ON dc.id = cu.diveClub.id " +
            "WHERE dc.id = %d ";
}
